package com.mahdi.assignment.shoppingapp.fakes

import com.mahdi.assignment.shoppingapp.core.network.ApiService
import com.mahdi.assignment.shoppingapp.feature.search.data.remote.SearchResponse
import com.mahdi.assignment.shoppingapp.fixtures.ProductFixtures

class FakeApiService() : ApiService {
    var shouldReturnError = false

    override suspend fun searchProducts(query: String, page: Int): SearchResponse {
        if (shouldReturnError) {
            throw Exception("Simulated network failure")
        }
        return SearchResponse(
            products = ProductFixtures.fakeProducts,
            currentPage = 1,
            pageSize = 10,
            totalResults = 1,
            pageCount = 1
        )
    }
}