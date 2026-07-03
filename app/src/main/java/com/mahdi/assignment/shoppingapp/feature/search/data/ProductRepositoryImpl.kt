package com.mahdi.assignment.shoppingapp.feature.search.data

import com.mahdi.assignment.shoppingapp.core.common.DispatcherProvider
import com.mahdi.assignment.shoppingapp.core.network.ApiService
import com.mahdi.assignment.shoppingapp.core.network.NetworkErrorHandler
import com.mahdi.assignment.shoppingapp.feature.search.data.remote.toDomain
import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import com.mahdi.assignment.shoppingapp.core.common.Result
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val dispatchers: DispatcherProvider
) : ProductRepository {
    override fun searchProducts(query: String, page: Int) = flow {
        try {
            emit(Result.Loading)
            val response = apiService.searchProducts(query, page)
            emit(Result.Success(response.toDomain()))
        } catch (e: Exception) {
            val errorMessage = NetworkErrorHandler.getErrorMessage(e)
            emit(Result.Error(e, errorMessage))
        }
    }.flowOn(dispatchers.io)
}