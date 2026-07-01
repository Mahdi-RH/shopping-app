package com.mahdi.assignment.shoppingapp.core.network

import com.mahdi.assignment.shoppingapp.feature.search.data.remote.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun searchProducts(
        @Query("que") query: String,
        @Query("page") page: Int = 1
    ): SearchResponse
}
