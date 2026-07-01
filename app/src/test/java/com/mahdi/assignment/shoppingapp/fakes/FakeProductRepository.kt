package com.mahdi.assignment.shoppingapp.fakes

import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult
import com.mahdi.assignment.shoppingapp.core.common.Result
import com.mahdi.assignment.shoppingapp.feature.search.data.remote.toDomain
import com.mahdi.assignment.shoppingapp.fixtures.ProductFixtures

class FakeProductRepository : ProductRepository {

    var shouldReturnError = false
    var fakeSearchResult = SearchResult(
        products = ProductFixtures.fakeProducts.map { it.toDomain() },
        currentPage = 1,
        pageSize = 10,
        totalResults = 0,
        pageCount = 1
    )

    override suspend fun searchProducts(query: String, page: Int): Result<SearchResult> {
        return if (shouldReturnError) {
            Result.Error(Exception("Test error"), "Test error")
        } else {
            Result.Success(fakeSearchResult)
        }
    }
}
