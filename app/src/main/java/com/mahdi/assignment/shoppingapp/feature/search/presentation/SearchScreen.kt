package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        SearchInputField(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged
        )

        Spacer(Modifier.height(16.dp))

        when (uiState) {
            is SearchUiState.Idle -> EmptyState()
            is SearchUiState.Loading -> LoadingState()
            is SearchUiState.Success -> ProductList(
                products = (uiState as SearchUiState.Success).results.products,
                onLoadMore = { viewModel.loadNextPage() },
                onRetry = { viewModel.retryLoadNextPage() }
            )
            is SearchUiState.Error -> ErrorState(
                message = (uiState as SearchUiState.Error).message,
                onRetry = { viewModel.retryLoadNextPage() }
            )
        }
    }
}
