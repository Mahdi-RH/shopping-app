package com.mahdi.assignment.shoppingapp.fakes

import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.SearchResult
import com.mahdi.assignment.shoppingapp.core.common.Result
import com.mahdi.assignment.shoppingapp.feature.search.data.remote.toDomain
import com.mahdi.assignment.shoppingapp.fixtures.ProductFixtures
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProductRepository : ProductRepository {

    var shouldReturnError = false
     var searchCallCount = 0
    private var fakeSearchResult = SearchResult(
        products = ProductFixtures.fakeProducts.map { it.toDomain() },
        currentPage = 1,
        pageSize = 10,
        totalResults = 20,
        pageCount = 2
    )

    override fun searchProducts(query: String, page: Int): Flow<Result<SearchResult>> = flow {
        searchCallCount++
        if (shouldReturnError) {
            emit(Result.Error(Exception("Test error"), "Test error"))
        } else {
            emit(Result.Success(fakeSearchResult))
        }
    }

    fun resetCallCount() {
        searchCallCount = 0
    }
}
