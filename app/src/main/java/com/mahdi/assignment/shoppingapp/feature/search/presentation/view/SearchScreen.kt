package com.mahdi.assignment.shoppingapp.feature.search.presentation.view

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mahdi.assignment.shoppingapp.feature.search.presentation.SearchViewModel
import com.mahdi.assignment.shoppingapp.feature.search.presentation.model.SearchEvent
import com.mahdi.assignment.shoppingapp.feature.search.presentation.model.SearchUiState
import com.mahdi.assignment.shoppingapp.ui.theme.PreviewData
import com.mahdi.assignment.shoppingapp.ui.theme.ShoppingAppTheme
import com.mahdi.assignment.shoppingapp.ui.theme.Spacing
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier, viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is SearchEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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
            query = uiState.query, onQueryChange = onQueryChange
        )

        Spacer(Modifier.height(Spacing.medium))

        when {
            uiState.isInitialLoading -> {
                LoadingState()
            }

            uiState.products.isEmpty() && uiState.errorMessage != null  -> {
                ErrorState(
                    message = uiState.errorMessage, onRetry = onRetry
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

@Preview(showBackground = true, name = "Success State - Light")
@Preview(
    showBackground = true, name = "Success State - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SearchScreenSuccessPreview() {
    ShoppingAppTheme {
        Surface {
            SearchScreenContent(
                uiState = SearchUiState(
                products = PreviewData.products, query = "Phone"
            ), onQueryChange = {}, onLoadMore = {}, onRetryLoadMore = {}, onRetry = {})
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun SearchScreenLoadingPreview() {
    ShoppingAppTheme {
        Surface {
            SearchScreenContent(
                uiState = SearchUiState(isInitialLoading = true, query = "Laptop"),
                onQueryChange = {},
                onLoadMore = {},
                onRetryLoadMore = {},
                onRetry = {})
        }
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun SearchScreenErrorPreview() {
    ShoppingAppTheme {
        Surface {
            SearchScreenContent(
                uiState = SearchUiState(
                errorMessage = "No internet connection", query = "Tablet"
            ), onQueryChange = {}, onLoadMore = {}, onRetryLoadMore = {}, onRetry = {})
        }
    }
}

