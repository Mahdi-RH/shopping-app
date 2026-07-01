package com.mahdi.assignment.shoppingapp.repository

import com.mahdi.assignment.shoppingapp.fakes.FakeApiService
import com.mahdi.assignment.shoppingapp.feature.search.data.ProductRepositoryImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import com.mahdi.assignment.shoppingapp.core.common.Result
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    private lateinit var fakeApiService: FakeApiService
    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setup() {
        fakeApiService = FakeApiService()
        repository = ProductRepositoryImpl(fakeApiService)
    }

    @Test
    fun `searchProducts returns success when api succeeds`() = runBlocking {
        when (val result = repository.searchProducts("test").first()) {
            is Result.Success -> {
                val data = result.data
                assertEquals(10, data.products.size)
            }
            else -> fail("Expected Success but got $result")
        }
    }

    @Test
    fun `searchProducts returns failure when api fails`() = runBlocking {
        fakeApiService.shouldReturnError = true

        when (val result = repository.searchProducts("test").first()) {
            is Result.Error -> {
                assertNotNull(result.exception)
            }
            else -> fail("Expected Error but got $result")
        }
    }
}