package com.mahdi.assignment.shoppingapp.feature.search.presentation.model

import com.mahdi.assignment.shoppingapp.feature.search.domain.model.Product

data class SearchUiState(
    val query: String = "",
    val products: List<Product> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String? = null,
    val loadMoreError: Boolean = false,
    val currentPage: Int = 1,
    val totalPages: Int = 1
) {
    val isIdle: Boolean get() = query.isBlank() && products.isEmpty() && !isInitialLoading
    val canLoadMore: Boolean get() = !isLoadingMore && currentPage < totalPages
}
