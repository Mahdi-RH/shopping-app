package com.mahdi.assignment.shoppingapp.feature.search.data.remote

data class SearchResponse(
    val products: List<Product>,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int
)