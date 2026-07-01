package com.mahdi.assignment.shoppingapp.feature.search.data.remote

import com.mahdi.assignment.shoppingapp.feature.search.domain.model.Product

data class ProductResponse(
    val productId: Int,
    val productName: String,
    val reviewInformation: ReviewInformationResponse,
    val USPs: List<String>,
    val availabilityState: Int,
    val salesPriceIncVat: Double,
    val productImage: String,
    val nextDayDelivery: Boolean,
    val promoIcon: PromoIconResponse? = null,
    val listPriceIncVat: Double? = null,
    val listPriceExVat: Double? = null,
    val coolbluesChoiceInformationTitle: String? = null
)
fun ProductResponse.toDomain() = Product(
    id = productId,
    name = productName,
    averageRating = reviewInformation.reviewSummary.reviewAverage,
    reviewCount = reviewInformation.reviewSummary.reviewCount,
    USPs = USPs,
    availabilityState = availabilityState,
    priceIncludingVat = salesPriceIncVat,
    imageUrl = productImage,
    hasNextDayDelivery = nextDayDelivery,
    promoText = promoIcon?.text,
    listPriceIncludingVat = listPriceIncVat
)