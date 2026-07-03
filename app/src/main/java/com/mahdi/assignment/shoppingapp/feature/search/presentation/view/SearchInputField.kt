package com.mahdi.assignment.shoppingapp.feature.search.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mahdi.assignment.shoppingapp.R
import com.mahdi.assignment.shoppingapp.ui.theme.ShoppingAppTheme

@Composable
fun SearchInputField(
    query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.search_products)) },
        singleLine = true,
        maxLines = 1
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(
    showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SearchInputFieldPreview() {
    ShoppingAppTheme {
        Surface {
            var text by remember { mutableStateOf("iPhone") }

            SearchInputField(
                query = text, onQueryChange = { text = it })
        }
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
private fun SearchInputFieldEmptyPreview() {
    ShoppingAppTheme {
        Surface {
            SearchInputField(
                query = "", onQueryChange = {})
        }
    }
}
