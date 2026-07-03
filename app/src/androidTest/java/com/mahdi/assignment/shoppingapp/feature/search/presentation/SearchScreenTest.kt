package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.Product
import com.mahdi.assignment.shoppingapp.feature.search.presentation.model.SearchUiState
import com.mahdi.assignment.shoppingapp.feature.search.presentation.view.SearchScreenContent
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun idleState_showsSearchHint() {
        composeTestRule.setContent {
            SearchScreenContent(
                uiState = SearchUiState(),
                onQueryChange = {},
                onRetry = {},
                onLoadMore = {},
                onRetryLoadMore = {})
        }

        composeTestRule.onNodeWithText("Start typing to search for products").assertIsDisplayed()
    }

    @Test
    fun loadingState_showsCircularProgressIndicator() {
        composeTestRule.setContent {
            SearchScreenContent(
                uiState = SearchUiState(isInitialLoading = true),
                onQueryChange = {},
                onRetry = {},
                onLoadMore = {},
                onRetryLoadMore = {})
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }


    @Test
    fun successState_showsProductList() {
        val products = listOf(
            Product(
                id = 1,
                name = "Product 1",
                averageRating = 4.5,
                reviewCount = 10,
                USPs = listOf("USP 1"),
                availabilityState = 1,
                priceIncludingVat = 100.0,
                imageUrl = "",
                hasNextDayDelivery = true
            ), Product(
                id = 2,
                name = "Product 2",
                averageRating = 4.0,
                reviewCount = 5,
                USPs = listOf("USP 2"),
                availabilityState = 1,
                priceIncludingVat = 50.0,
                imageUrl = "",
                hasNextDayDelivery = false
            )
        )

        composeTestRule.setContent {
            SearchScreenContent(
                uiState = SearchUiState(products = products, isInitialLoading = false),
                onQueryChange = {},
                onRetry = {},
                onLoadMore = {},
                onRetryLoadMore = {})
        }

        composeTestRule.onNodeWithText("Product 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Product 2").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessageAndRetryButton() {
        val errorMessage = "Network Error"
        composeTestRule.setContent {
            SearchScreenContent(
                uiState = SearchUiState(
                query = "test", errorMessage = errorMessage, isInitialLoading = false
            ), onQueryChange = {}, onRetry = {}, onLoadMore = {}, onRetryLoadMore = {})
        }

        composeTestRule.onNodeWithText("Network Error").assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun paginationError_showsRetryButtonAtBottom() {
        val products = listOf(
            Product(
                id = 1,
                name = "Product 1",
                averageRating = 4.5,
                reviewCount = 10,
                USPs = listOf("USP 1"),
                availabilityState = 1,
                priceIncludingVat = 100.0,
                imageUrl = "",
                hasNextDayDelivery = true
            )
        )

        composeTestRule.setContent {
            SearchScreenContent(
                uiState = SearchUiState(
                products = products, loadMoreError = true
            ), onQueryChange = {}, onRetry = {}, onLoadMore = {}, onRetryLoadMore = {})
        }

        composeTestRule.onNodeWithText("Retry loading more").assertIsDisplayed()
    }

    @Test
    fun typingInSearchField_triggersCallback() {
        var capturedQuery = ""
        composeTestRule.setContent {
            SearchScreenContent(
                uiState = SearchUiState(),
                onQueryChange = { capturedQuery = it },
                onRetry = {},
                onLoadMore = {},
                onRetryLoadMore = {})
        }

        composeTestRule.onNodeWithText("Search products").performTextInput("iphone")

        assert(capturedQuery == "iphone")
    }
}
