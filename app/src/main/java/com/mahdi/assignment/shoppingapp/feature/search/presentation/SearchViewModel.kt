package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import kotlinx.coroutines.Job
import com.mahdi.assignment.shoppingapp.core.common.Result
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.Product
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ProductRepository
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
        _searchQuery
            .debounce(300) // 300 ms debounce to limit API calls while typing
            .distinctUntilChanged()
            .onEach { query ->
                resetPagination()
                if (query.isBlank()) {
                    _uiState.value = SearchUiState.Idle
                } else {
                    performSearch(query)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            _uiState.value = SearchUiState.Idle
            searchJob?.cancel()
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
    private fun performSearch(query: String) {
        searchJob?.cancel() // Cancel any ongoing request
        searchJob = viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            repository.searchProducts(query).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _products.clear()
                        _products.addAll(result.data.products)
                        totalPages = result.data.pageCount
                        _uiState.value = SearchUiState.Success(
                            results = SearchResult(
                                products = _products.toList(),
                                currentPage = currentPage,
                                pageSize = result.data.pageSize,
                                totalResults = result.data.totalResults,
                                pageCount = totalPages
                            )
                        )                    }
                    is Result.Error -> _uiState.value = SearchUiState.Error(result.message ?: "Unknown error")
                    is Result.Loading -> _uiState.value = SearchUiState.Loading
                }
            }
        }
    }
    // Load next page (pagination)
    fun loadNextPage() {
        if (isLoadingMore || currentPage >= totalPages) return
        isLoadingMore = true
        isErrorLoadingMore = false
        paginationJob?.cancel()
        paginationJob = viewModelScope.launch {
            repository.searchProducts(_searchQuery.value, currentPage + 1).collect { result ->
                when (result) {
                    is Result.Success -> {
                        currentPage += 1
                        _products.addAll(result.data.products)
                        _uiState.value = SearchUiState.Success(
                            results = SearchResult(
                                products = _products.toList(),
                                currentPage = currentPage,
                                pageSize = result.data.pageSize,
                                totalResults = result.data.totalResults,
                                pageCount = totalPages
                            )
                        )
                        isLoadingMore = false
                    }
                    is Result.Error -> {
                        isLoadingMore = false
                        isErrorLoadingMore = true
                        // Optionally expose a pagination error state if needed
                    }
                    is Result.Loading -> {
                        _uiState.value = SearchUiState.Loading
                    }
                }
            }
        }
    }
    // Retry loading next page after error
    fun retryLoadNextPage() {
        loadNextPage()
    }
}
