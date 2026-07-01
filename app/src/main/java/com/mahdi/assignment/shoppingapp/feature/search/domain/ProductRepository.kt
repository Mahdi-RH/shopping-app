package com.mahdi.assignment.shoppingapp.feature.search.domain

import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult
import com.mahdi.assignment.shoppingapp.core.common.Result

interface ProductRepository {
    suspend fun searchProducts(query: String, page: Int = 1): Result<SearchResult>
}
