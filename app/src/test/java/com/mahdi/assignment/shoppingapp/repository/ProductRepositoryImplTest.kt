package com.mahdi.assignment.shoppingapp.repository

import com.mahdi.assignment.shoppingapp.core.common.DispatcherProvider
import com.mahdi.assignment.shoppingapp.fakes.FakeApiService
import com.mahdi.assignment.shoppingapp.feature.search.data.ProductRepositoryImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import com.mahdi.assignment.shoppingapp.core.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs

class ProductRepositoryImplTest {

    private lateinit var fakeApiService: FakeApiService
    private lateinit var repository: ProductRepositoryImpl
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testDispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher = testDispatcher
        override val io: CoroutineDispatcher = testDispatcher
        override val default: CoroutineDispatcher = testDispatcher
        override val unconfined: CoroutineDispatcher = testDispatcher
    }

    @Before
    fun setup() {
        fakeApiService = FakeApiService()
        repository = ProductRepositoryImpl(fakeApiService, testDispatcherProvider)
    }

    @Test
    fun `searchProducts returns success when api succeeds`() = runTest {
        val results = repository.searchProducts("test").toList()

        assertIs<Result.Loading>(results[0])

        val successResult = results[1]
        assertIs<Result.Success<*>>(successResult)

        val data = (successResult as Result.Success).data
        assertEquals(10, data.products.size)
    }

    @Test
    fun `searchProducts returns failure when api fails`() = runTest {
        fakeApiService.shouldReturnError = true

        val results = repository.searchProducts("test").toList()

        assertIs<Result.Loading>(results[0])

        val errorResult = results[1]
        assertIs<Result.Error>(errorResult)
        assertNotNull(errorResult.exception)
    }
}