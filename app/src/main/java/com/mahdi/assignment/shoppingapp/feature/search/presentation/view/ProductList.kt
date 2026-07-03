package com.mahdi.assignment.shoppingapp.feature.search.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.Product
import com.mahdi.assignment.shoppingapp.ui.theme.PreviewData
import com.mahdi.assignment.shoppingapp.ui.theme.ShoppingAppTheme

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
        modifier = modifier.fillMaxSize(), state = listState
    ) {
        items(products, key = { product -> product.id }) { product ->
            ProductCard(product = product)
        }

        if (isLoadingMore || isErrorLoadingMore) {
            item {
                PaginationFooter(
                    isLoading = isLoadingMore,
                    isError = isErrorLoadingMore,
                    onRetry = { onRetry() })
            }
        }
    }

    LaunchedEffect(listState, products.size) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { index ->
                if (index != null && index >= products.lastIndex && products.isNotEmpty()) {
                    onLoadMore()
                }
            }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(
    showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProductListPreview() {
    ShoppingAppTheme {
        Surface {
            ProductList(
                products = PreviewData.products,
                onLoadMore = {},
                onRetry = {},
                isLoadingMore = true
            )
        }
    }
}
