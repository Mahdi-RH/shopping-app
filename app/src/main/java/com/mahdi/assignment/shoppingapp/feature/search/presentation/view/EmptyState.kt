package com.mahdi.assignment.shoppingapp.feature.search.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mahdi.assignment.shoppingapp.R
import com.mahdi.assignment.shoppingapp.ui.theme.ShoppingAppTheme

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.search_hint))
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun EmptyStatePreview() {
    ShoppingAppTheme {
        Surface {
            EmptyState()
        }
    }
}

