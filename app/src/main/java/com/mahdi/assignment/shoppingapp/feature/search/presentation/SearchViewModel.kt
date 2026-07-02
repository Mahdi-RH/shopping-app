package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.assignment.shoppingapp.core.common.DispatcherProvider
import com.mahdi.assignment.shoppingapp.core.common.Result
import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult
import com.mahdi.assignment.shoppingapp.feature.search.presentation.model.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null
    private var loadMoreJob: Job? = null

    init {
        observeSearchQuery()
    }
    private fun observeSearchQuery() {
        uiState
            .map { it.query }
            .debounce(300)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { query ->
                search(query)
            }
            .launchIn(viewModelScope)
    }

    private fun search(query: String) {
        searchJob?.cancel()
        loadMoreJob?.cancel()

        searchJob = viewModelScope.launch(dispatchers.main) {
            repository.searchProducts(query, page = 1)
                .collect { result ->
                    handleSearchResult(result)
                }
        }
    }

    private fun handleSearchResult(result: Result<SearchResult>) {
        when (result) {
            is Result.Loading -> {
                _uiState.update {
                    it.copy(
                        isInitialLoading = true,
                        errorMessage = null,
                        products = emptyList(),
                        currentPage = 1,
                        totalPages = 1,
                        loadMoreError = false
                    )
                }
            }

            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        products = result.data.products,
                        isInitialLoading = false,
                        errorMessage = null,
                        currentPage = result.data.currentPage,
                        totalPages = result.data.pageCount
                    )
                }
            }

            is Result.Error -> {
                _uiState.update {
                    it.copy(
                        isInitialLoading = false,
                        errorMessage = result.message ?: "Unknown error",
                        products = emptyList()
                    )
                }
            }
        }
    }

    fun loadNextPage() {
        val state = _uiState.value

        if (!state.canLoadMore || state.isInitialLoading) return

        loadMoreJob?.cancel()
        loadMoreJob = viewModelScope.launch(dispatchers.main) {
            repository.searchProducts(
                query = state.query,
                page = state.currentPage + 1
            ).collect { result ->
                handleLoadMoreResult(result)
            }
        }
    }

    private fun handleLoadMoreResult(result: Result<SearchResult>) {
        when (result) {
            is Result.Loading -> {
                _uiState.update {
                    it.copy(
                        isLoadingMore = true,
                        loadMoreError = false
                    )
                }
            }

            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        products = it.products + result.data.products,
                        isLoadingMore = false,
                        loadMoreError = false,
                        currentPage = result.data.currentPage,
                        totalPages = result.data.pageCount
                    )
                }
            }

            is Result.Error -> {
                _uiState.update {
                    it.copy(
                        isLoadingMore = false,
                        loadMoreError = true
                    )
                }
            }
        }
    }

    fun onQueryChanged(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                products = if (query.isBlank()) emptyList() else it.products,
                errorMessage = null,
                loadMoreError = false,
                currentPage = if (query.isBlank()) 1 else it.currentPage,
                totalPages = if (query.isBlank()) 1 else it.totalPages
            )
        }
    }

    fun retrySearch() {
        val query = _uiState.value.query
        if (query.isNotBlank()) search(query)
    }

    fun retryLoadNextPage() {
        loadNextPage()
    }
}