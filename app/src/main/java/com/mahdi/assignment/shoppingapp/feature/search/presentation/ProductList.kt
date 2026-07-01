package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.Product

@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product>,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    isLoadingMore: Boolean = false,
    isErrorLoadingMore: Boolean = false
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState
    ) {
        items(products) { product ->
            ProductCard(product = product)
        }

        item {
            when {
                isLoadingMore -> LoadingMoreFooter()
                isErrorLoadingMore -> RetryMoreFooter(onRetry)
            }
        }
    }

    // Trigger load more when near the list end
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { index ->
                if (index != null && index >= products.lastIndex) {
                    onLoadMore()
                }
            }
    }
}
