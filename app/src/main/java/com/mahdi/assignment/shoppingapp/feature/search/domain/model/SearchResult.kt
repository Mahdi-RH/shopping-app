package com.mahdi.assignment.shoppingapp.feature.search.domain.model

data class SearchResult(
    val products: List<Product>,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int
)