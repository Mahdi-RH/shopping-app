package com.mahdi.assignment.shoppingapp.feature.search.domain.model

data class Product(
    val id: Int,
    val name: String,
    val averageRating: Double,
    val reviewCount: Int,
    val USPs: List<String>,
    val availabilityState: Int,
    val priceIncludingVat: Double,
    val imageUrl: String,
    val hasNextDayDelivery: Boolean,
    val promoText: String? = null,
    val listPriceIncludingVat: Double? = null
)