package com.mahdi.assignment.shoppingapp.feature.search.data.remote

data class Product(
    val productId: Int,
    val productName: String,
    val reviewInformation: ReviewInformation,
    val USPs: List<String>,
    val availabilityState: Int,
    val salesPriceIncVat: Double,
    val productImage: String,
    val nextDayDelivery: Boolean,
    val promoIcon: PromoIcon? = null,
    val listPriceIncVat: Double? = null,
    val listPriceExVat: Double? = null,
    val coolbluesChoiceInformationTitle: String? = null
)