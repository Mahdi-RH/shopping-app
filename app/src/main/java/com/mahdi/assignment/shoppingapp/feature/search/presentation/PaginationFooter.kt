package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mahdi.assignment.shoppingapp.ui.theme.Dimens

@Composable
fun PaginationFooter(
    isLoading: Boolean,
    isError: Boolean,
    onRetry: () -> Unit
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