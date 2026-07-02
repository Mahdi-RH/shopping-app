package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mahdi.assignment.shoppingapp.feature.search.presentation.model.SearchUiState
import com.mahdi.assignment.shoppingapp.ui.theme.Spacing


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SearchScreenContent(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChanged,
        onLoadMore = { viewModel.loadNextPage() },
        onRetryLoadMore = { viewModel.retryLoadNextPage() },
        onRetry = viewModel::retrySearch,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun SearchScreenContent(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onLoadMore: () -> Unit,
    onRetryLoadMore: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(Spacing.medium)
            .imePadding()
    ) {
        SearchInputField(
            query = uiState.query,
            onQueryChange = onQueryChange
        )

        Spacer(Modifier.height(Spacing.medium))

        when {
            uiState.isInitialLoading -> {
                LoadingState()
            }

            uiState.errorMessage != null -> {
                ErrorState(
                    message = uiState.errorMessage,
                    onRetry = onRetry
                )
            }

            uiState.products.isNotEmpty() -> {
                ProductList(
                    products = uiState.products,
                    onLoadMore = onLoadMore,
                    onRetry = onRetryLoadMore,
                    isLoadingMore = uiState.isLoadingMore,
                    isErrorLoadingMore = uiState.loadMoreError
                )
            }

            uiState.isIdle -> {
                EmptyState()
            }
        }
    }
}
