package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import kotlinx.coroutines.Job
import com.mahdi.assignment.shoppingapp.core.common.Result
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

    private var searchJob: Job? = null

    init {
        _searchQuery
            .debounce(300) // 300 ms debounce to limit API calls while typing
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .onEach { query ->
                performSearch(query)
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
    private fun performSearch(query: String) {
        searchJob?.cancel() // Cancel any ongoing request
        searchJob = viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            repository.searchProducts(query).collect { result ->
                when (result) {
                    is Result.Success -> _uiState.value = SearchUiState.Success(result.data)
                    is Result.Error -> _uiState.value = SearchUiState.Error(result.message ?: "Unknown error")
                    is Result.Loading -> _uiState.value = SearchUiState.Loading
                }
            }
        }
    }
}
