package com.mahdi.assignment.shoppingapp.feature.search.data.remote

import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult

data class SearchResponse(
    val products: List<ProductResponse>,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int
)

fun SearchResponse.toDomain() =
    SearchResult(
        products = products.map { it.toDomain() },
        currentPage = currentPage,
        pageSize = pageSize,
        totalResults = totalResults,
        pageCount = pageCount
    )
