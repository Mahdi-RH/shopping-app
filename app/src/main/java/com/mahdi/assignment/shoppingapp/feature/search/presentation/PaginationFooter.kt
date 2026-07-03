package com.mahdi.assignment.shoppingapp.feature.search.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mahdi.assignment.shoppingapp.ui.theme.Dimens
import com.mahdi.assignment.shoppingapp.ui.theme.ShoppingAppTheme

@Composable
fun PaginationFooter(
    isLoading: Boolean, isError: Boolean, onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = Dimens.loadingFooterMinHeight),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(Dimens.footerLoaderSize))
        } else if (isError) {
            RetryMoreFooter(onRetry = onRetry)
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(
    showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PaginationFooterPreview() {
    ShoppingAppTheme {
        Surface {
            Column {
                PaginationFooter(
                    isLoading = true, isError = false, onRetry = {})

                PaginationFooter(
                    isLoading = false, isError = true, onRetry = {})
            }
        }
    }
}
