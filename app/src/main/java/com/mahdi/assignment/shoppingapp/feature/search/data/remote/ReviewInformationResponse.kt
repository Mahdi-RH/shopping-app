package com.mahdi.assignment.shoppingapp.feature.search.data.remote

data class ReviewInformationResponse(
    val reviews: List<String>,
    val reviewSummary: ReviewSummaryResponse
)