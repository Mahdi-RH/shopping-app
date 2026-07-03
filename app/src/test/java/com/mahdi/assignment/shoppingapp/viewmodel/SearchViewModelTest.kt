package com.mahdi.assignment.shoppingapp.viewmodel

import com.mahdi.assignment.shoppingapp.core.common.DispatcherProvider
import com.mahdi.assignment.shoppingapp.fakes.FakeProductRepository
import com.mahdi.assignment.shoppingapp.feature.search.presentation.SearchViewModel
import com.mahdi.assignment.shoppingapp.feature.search.presentation.model.SearchUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var fakeRepository: FakeProductRepository
    private lateinit var viewModel: SearchViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testDispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher = testDispatcher
        override val io: CoroutineDispatcher = testDispatcher
        override val default: CoroutineDispatcher = testDispatcher
        override val unconfined: CoroutineDispatcher = testDispatcher
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeRepository = FakeProductRepository()
        viewModel = SearchViewModel(fakeRepository,testDispatcherProvider)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has isIdle set to true`() = runTest {
        val state = viewModel.uiState.value
        assertTrue(state.isIdle)
        assertFalse(state.isInitialLoading)
        assertTrue(state.products.isEmpty())
    }

    @Test
    fun `search query returns state with products`() = runTest {
        val states = mutableListOf<SearchUiState>()
        val job = backgroundScope.launch {
            viewModel.uiState.toList(states)
        }

        viewModel.onQueryChanged("test")

        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertFalse(finalState.isIdle)
        assertFalse(finalState.isInitialLoading)
        assertTrue(finalState.products.isNotEmpty())

        job.cancel()
    }

    @Test
    fun `search returns error state when repository fails`() = runTest {
        fakeRepository.shouldReturnError = true
        val states = mutableListOf<SearchUiState>()

        val job = backgroundScope.launch {
            viewModel.uiState.toList(states)
        }

        viewModel.onQueryChanged("fail")

        advanceUntilIdle()

        val errorState = states.find { it.errorMessage != null }
        assertNotNull(errorState, "A state with an error message should have been emitted")
        assertFalse(errorState.isInitialLoading)

        job.cancel()
    }

    @Test
    fun `empty query resets state to idle`() = runTest {
        viewModel.onQueryChanged("query")

        advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isIdle)

        viewModel.onQueryChanged("")

        val finalState = viewModel.uiState.value
        assertTrue(finalState.isIdle)
        assertTrue(finalState.products.isEmpty())
    }

    @Test
    fun `multiple query changes within debounce period only triggers one search`() = runTest {
        viewModel.onQueryChanged("i")
        advanceTimeBy(100)
        viewModel.onQueryChanged("ip")
        advanceTimeBy(100)
        viewModel.onQueryChanged("iph")

        advanceUntilIdle()

        assertEquals(1, fakeRepository.searchCallCount)
    }

    @Test
    fun `onLoadMore appends products to existing list`() = runTest {

        viewModel.search("initial")
        advanceUntilIdle()
        val initialSize = viewModel.uiState.value.products.size

        viewModel.loadNextPage()
        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(initialSize + 10, finalState.products.size)
        assertFalse(finalState.isLoadingMore)
    }

    @Test
    fun `load more error preserves existing products`() = runTest {

        viewModel.search("test")
        advanceUntilIdle()

        fakeRepository.shouldReturnError = true
        viewModel.loadNextPage()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.products.isNotEmpty())
        assertTrue(state.loadMoreError)
    }
}
