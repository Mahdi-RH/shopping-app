package com.mahdi.assignment.shoppingapp.feature.search.presentation.model

sealed class SearchEvent {
    data class ShowError(val message: String) : SearchEvent()
}