package com.mahdi.assignment.shoppingapp.feature.search.data

import com.mahdi.assignment.shoppingapp.core.network.ApiService
import com.mahdi.assignment.shoppingapp.feature.search.data.remote.toDomain
import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult
import com.mahdi.assignment.shoppingapp.core.common.Result

class ProductRepositoryImpl(
    private val apiService: ApiService
) : ProductRepository {
    override suspend fun searchProducts(query: String, page: Int): Result<SearchResult> {
        return try {
            val response = apiService.searchProducts(query, page)
            Result.Success(response.toDomain())
        } catch (e: Exception) {
            Result.Error(e, e.localizedMessage)
        }
    }
}