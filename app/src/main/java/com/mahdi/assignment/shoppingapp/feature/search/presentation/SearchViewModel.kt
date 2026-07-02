package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.assignment.shoppingapp.core.common.DispatcherProvider
import com.mahdi.assignment.shoppingapp.core.common.Result
import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.Product
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult
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

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    private var currentPage = 1
    private var totalPages = 1
    private var isLoadingMore = false
    private var isErrorLoadingMore = false
    private val _products = mutableListOf<Product>()
    private var searchJob: Job? = null
    private var paginationJob: Job? = null

    init {
        observeSearchQuery()
    }
    private fun observeSearchQuery() {
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { query ->
                resetPagination()
                performSearch(query)
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            clearSearch()
        }
    }

    private fun clearSearch() {
        resetPagination()
        _uiState.value = SearchUiState.Idle
    }

    fun retry() {
        val query = _searchQuery.value
        if (query.isNotBlank()) {
            resetPagination()
            performSearch(query)
        }
    }

    private fun resetPagination() {
        currentPage = 1
        totalPages = 1
        _products.clear()
        isLoadingMore = false
        isErrorLoadingMore = false
        searchJob?.cancel()
        paginationJob?.cancel()
    }
    private fun emitSuccessState(data: SearchResult) {
        _uiState.update {
            SearchUiState.Success(
                results = data.copy(products = _products.toList()),
                isLoadingMore = isLoadingMore,
                isErrorLoadingMore = isErrorLoadingMore
            )
        }
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatchers.main) {
            _uiState.value = SearchUiState.Loading

            repository.searchProducts(query).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _products.clear()
                        _products.addAll(result.data.products)
                        totalPages = result.data.pageCount
                        currentPage = result.data.currentPage
                        emitSuccessState(result.data)
                    }
                    is Result.Error -> {
                        _uiState.value = SearchUiState.Error(result.message ?: "Unknown error")
                    }
                    is Result.Loading -> {
                        _uiState.value = SearchUiState.Loading
                    }
                }
            }
        }
    }

    fun loadNextPage() {
        val currentState = _uiState.value
        if (isLoadingMore || currentPage >= totalPages || currentState !is SearchUiState.Success) return

        isLoadingMore = true
        isErrorLoadingMore = false
        emitSuccessState(currentState.results)
        paginationJob?.cancel()
        paginationJob = viewModelScope.launch(dispatchers.main) {
            repository.searchProducts(_searchQuery.value, currentPage + 1).collect { result ->
                when (result) {
                    is Result.Success -> {
                        isLoadingMore = false
                        currentPage = result.data.currentPage
                        _products.addAll(result.data.products)
                        emitSuccessState(result.data)
                    }
                    is Result.Error -> {
                        isLoadingMore = false
                        isErrorLoadingMore = true
                        emitSuccessState(currentState.results)
                    }
                    is Result.Loading -> {
                        // Handled by flags
                    }
                }
            }
        }
    }
    fun retryLoadNextPage() {
        loadNextPage()
    }
}
