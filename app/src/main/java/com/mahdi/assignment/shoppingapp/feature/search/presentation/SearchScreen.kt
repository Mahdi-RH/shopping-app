package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mahdi.assignment.shoppingapp.ui.theme.Spacing


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    SearchScreenContent(
        uiState = uiState,
        searchQuery = searchQuery,
        onQueryChange = viewModel::onSearchQueryChanged,
        onLoadMore = { viewModel.loadNextPage() },
        onRetryLoadMore = { viewModel.retryLoadNextPage() },
        onRetry = {
            viewModel.onSearchQueryChanged(searchQuery)
            viewModel.retry()
        },
        modifier = modifier
    )
}

@Composable
fun SearchScreenContent(
    uiState: SearchUiState,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onLoadMore: () -> Unit,
    onRetryLoadMore: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(Spacing.medium)) {
        SearchInputField(
            query = searchQuery,
            onQueryChange = onQueryChange
        )

        Spacer(Modifier.height(Spacing.medium))

        when (uiState) {
            is SearchUiState.Idle -> EmptyState()
            is SearchUiState.Loading -> LoadingState()
            is SearchUiState.Success -> {
                ProductList(
                    products = uiState.results.products,
                    onLoadMore = onLoadMore,
                    onRetry = onRetryLoadMore,
                    isLoadingMore = uiState.isLoadingMore,
                    isErrorLoadingMore = uiState.isErrorLoadingMore
                )
            }
            is SearchUiState.Error -> ErrorState(
                message = uiState.message,
                onRetry = onRetry
            )
        }
    }
}
