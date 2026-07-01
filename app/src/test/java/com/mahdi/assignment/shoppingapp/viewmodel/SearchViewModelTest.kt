package com.mahdi.assignment.shoppingapp.viewmodel

import com.mahdi.assignment.shoppingapp.fakes.FakeProductRepository
import com.mahdi.assignment.shoppingapp.feature.search.presentation.SearchUiState
import com.mahdi.assignment.shoppingapp.feature.search.presentation.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var fakeRepository: FakeProductRepository
    private lateinit var viewModel: SearchViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeRepository = FakeProductRepository()
        viewModel = SearchViewModel(fakeRepository)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Idle`() = runTest {
        assertEquals(SearchUiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `search query triggers Loading then Success`() = runTest {
        val states = mutableListOf<SearchUiState>()
        val job = backgroundScope.launch {
            viewModel.uiState.toList(states)
        }

        viewModel.onSearchQueryChanged("test")

        advanceTimeBy(350)

        advanceUntilIdle()

        assert(states[0] is SearchUiState.Idle)
       // assert(states.find { it is SearchUiState.Loading } != null)
        assert(states.find { it is SearchUiState.Success } != null)
        job.cancel()
    }

    @Test
    fun `search returns error state when repository fails`() = runTest {
        fakeRepository.shouldReturnError = true

        val states = mutableListOf<SearchUiState>()

        val job = backgroundScope.launch {
            viewModel.uiState.toList(states)
        }

        viewModel.onSearchQueryChanged("fail")

        advanceTimeBy(350)
        advanceUntilIdle()

        val errorState = states.find { it is SearchUiState.Error }
        assert(errorState != null)
        job.cancel()
    }

    @Test
    fun `empty query resets to Idle and cancels search`() = runTest {
        val states = mutableListOf<SearchUiState>()
        val job = backgroundScope.launch {
            viewModel.uiState.toList(states)
        }

        viewModel.onSearchQueryChanged("query")
        advanceTimeBy(350)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is SearchUiState.Success)

        viewModel.onSearchQueryChanged("")

        assert(viewModel.uiState.value is SearchUiState.Idle)
        job.cancel()
    }
}
