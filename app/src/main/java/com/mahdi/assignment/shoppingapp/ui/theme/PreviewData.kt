package com.mahdi.assignment.shoppingapp.ui.theme

import com.mahdi.assignment.shoppingapp.feature.search.domain.model.Product

object PreviewData {

    val sampleProduct = Product(
        id = 1,
        name = "Sample Product Name That Might Be Long",
        averageRating = 4.5,
        reviewCount = 128,
        USPs = listOf("Free shipping", "Top rated"),
        availabilityState = 1,
        priceIncludingVat = 299.99,
        imageUrl = "",
        hasNextDayDelivery = true,
        promoText = "10% OFF"
    )

    val products = listOf(
        Product(
            id = 1,
            name = "Product 1 with a fairly long name",
            averageRating = 4.5,
            reviewCount = 10,
            USPs = listOf("Free shipping"),
            availabilityState = 1,
            priceIncludingVat = 99.99,
            imageUrl = "",
            hasNextDayDelivery = true,
            promoText = "Sale"
        ), Product(
            id = 2,
            name = "Product 2",
            averageRating = 3.8,
            reviewCount = 5,
            USPs = listOf("Best seller"),
            availabilityState = 1,
            priceIncludingVat = 45.0,
            imageUrl = "",
            hasNextDayDelivery = false,
            promoText = null
        ), Product(
            id = 3,
            name = "Product 3",
            averageRating = 5.0,
            reviewCount = 1,
            USPs = emptyList(),
            availabilityState = 1,
            priceIncludingVat = 120.0,
            imageUrl = "",
            hasNextDayDelivery = true,
            promoText = null
        )
    )

}