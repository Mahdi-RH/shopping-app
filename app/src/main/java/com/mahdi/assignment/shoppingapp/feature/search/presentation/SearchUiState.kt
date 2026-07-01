package com.mahdi.assignment.shoppingapp.feature.search.presentation

import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult

sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val results: SearchResult) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}