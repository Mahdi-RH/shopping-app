package com.mahdi.assignment.shoppingapp.feature.search.domain

import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult
import com.mahdi.assignment.shoppingapp.core.common.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun searchProducts(query: String, page: Int = 1): Flow<Result<SearchResult>>
}
