package com.mahdi.assignment.shoppingapp.feature.search.data

import com.mahdi.assignment.shoppingapp.core.network.ApiService
import com.mahdi.assignment.shoppingapp.feature.search.data.remote.toDomain
import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import com.mahdi.assignment.shoppingapp.core.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepositoryImpl(
    private val apiService: ApiService
) : ProductRepository {
    override fun searchProducts(query: String, page: Int) = flow {
        try {
            emit(Result.Loading)
            val response = apiService.searchProducts(query, page)
            emit(Result.Success(response.toDomain()))
        } catch (e: Exception) {
            emit(Result.Error(e, e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}