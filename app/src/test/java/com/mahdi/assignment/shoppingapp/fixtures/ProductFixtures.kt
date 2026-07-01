package com.mahdi.assignment.shoppingapp.fixtures

import com.mahdi.assignment.shoppingapp.feature.search.data.remote.ProductResponse
import com.mahdi.assignment.shoppingapp.feature.search.data.remote.ReviewInformationResponse
import com.mahdi.assignment.shoppingapp.feature.search.data.remote.ReviewSummaryResponse

object ProductFixtures {

    val fakeProducts = listOf(
        ProductResponse(
            productId = 1,
            productName = "Apple iPhone 15 Pro 256GB Black Titanium",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Excellent camera", "Very fast", "Premium build"),
                reviewSummary = ReviewSummaryResponse(4.8, 432)
            ),
            USPs = listOf("A17 Pro chip", "48MP camera", "USB-C"),
            availabilityState = 2,
            salesPriceIncVat = 1299.0,
            productImage = "https://picsum.photos/400/400?random=1",
            nextDayDelivery = true,
            promoIcon = null,
            listPriceIncVat = 1399.0,
            listPriceExVat = 1156.20,
            coolbluesChoiceInformationTitle = "Coolblue's Choice"
        ),

        ProductResponse(
            productId = 2,
            productName = "Samsung Galaxy S25 Ultra 512GB",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Amazing zoom", "Great display"),
                reviewSummary = ReviewSummaryResponse(4.7, 388)
            ),
            USPs = listOf("200MP camera", "S Pen included", "120Hz AMOLED"),
            availabilityState = 2,
            salesPriceIncVat = 1399.0,
            productImage = "https://picsum.photos/400/400?random=2",
            nextDayDelivery = true,
            promoIcon = null,
            listPriceIncVat = 1499.0,
            listPriceExVat = 1238.84,
            coolbluesChoiceInformationTitle = null
        ),

        ProductResponse(
            productId = 3,
            productName = "Sony WH-1000XM5 Wireless Headphones",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Best ANC", "Comfortable"),
                reviewSummary = ReviewSummaryResponse(4.9, 1250)
            ),
            USPs = listOf("Noise cancelling", "30h battery", "Multipoint"),
            availabilityState = 2,
            salesPriceIncVat = 329.0,
            productImage = "https://picsum.photos/400/400?random=3",
            nextDayDelivery = true,
            promoIcon = null,
            listPriceIncVat = 379.0,
            listPriceExVat = 313.22
        ),

        ProductResponse(
            productId = 4,
            productName = "Apple MacBook Air M4 13-inch",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Silent", "Super battery"),
                reviewSummary = ReviewSummaryResponse(4.8, 214)
            ),
            USPs = listOf("M4 chip", "18-hour battery", "Liquid Retina"),
            availabilityState = 1,
            salesPriceIncVat = 1499.0,
            productImage = "https://picsum.photos/400/400?random=4",
            nextDayDelivery = false,
            promoIcon = null,
            listPriceIncVat = 1599.0,
            listPriceExVat = 1321.49
        ),

        ProductResponse(
            productId = 5,
            productName = "Nintendo Switch OLED White",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Beautiful screen", "Fun games"),
                reviewSummary = ReviewSummaryResponse(4.7, 1986)
            ),
            USPs = listOf("OLED display", "64GB storage", "Dock included"),
            availabilityState = 2,
            salesPriceIncVat = 339.0,
            productImage = "https://picsum.photos/400/400?random=5",
            nextDayDelivery = true,
            promoIcon = null
        ),

        ProductResponse(
            productId = 6,
            productName = "Dyson V15 Detect Absolute",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Powerful suction", "Easy to use"),
                reviewSummary = ReviewSummaryResponse(4.6, 512)
            ),
            USPs = listOf("Laser detection", "60 min runtime", "HEPA filter"),
            availabilityState = 2,
            salesPriceIncVat = 699.0,
            productImage = "https://picsum.photos/400/400?random=6",
            nextDayDelivery = true,
            promoIcon = null,
            listPriceIncVat = 749.0,
            listPriceExVat = 618.18
        ),

        ProductResponse(
            productId = 7,
            productName = "LG OLED55C4 55-inch OLED TV",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Perfect blacks", "Excellent gaming"),
                reviewSummary = ReviewSummaryResponse(4.9, 340)
            ),
            USPs = listOf("OLED Evo", "120Hz", "Dolby Vision"),
            availabilityState = 2,
            salesPriceIncVat = 1599.0,
            productImage = "https://picsum.photos/400/400?random=7",
            nextDayDelivery = false,
            promoIcon = null
        ),

        ProductResponse(
            productId = 8,
            productName = "JBL Charge 5 Bluetooth Speaker",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Loud", "Long battery"),
                reviewSummary = ReviewSummaryResponse(4.7, 913)
            ),
            USPs = listOf("20h battery", "IP67", "PartyBoost"),
            availabilityState = 2,
            salesPriceIncVat = 159.0,
            productImage = "https://picsum.photos/400/400?random=8",
            nextDayDelivery = true,
            promoIcon = null
        ),

        ProductResponse(
            productId = 9,
            productName = "Dell XPS 15 Laptop",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Premium design", "Fast performance"),
                reviewSummary = ReviewSummaryResponse(4.5, 154)
            ),
            USPs = listOf("Intel Core Ultra 7", "32GB RAM", "1TB SSD"),
            availabilityState = 0,
            salesPriceIncVat = 2299.0,
            productImage = "https://picsum.photos/400/400?random=9",
            nextDayDelivery = false,
            promoIcon = null,
            listPriceIncVat = 2399.0,
            listPriceExVat = 1982.64
        ),

        ProductResponse(
            productId = 10,
            productName = "Apple Watch Series 10 GPS 45mm",
            reviewInformation = ReviewInformationResponse(
                reviews = listOf("Excellent fitness tracking", "Smooth UI"),
                reviewSummary = ReviewSummaryResponse(4.8, 645)
            ),
            USPs = listOf("ECG", "Always-On Display", "Water resistant"),
            availabilityState = 2,
            salesPriceIncVat = 499.0,
            productImage = "https://picsum.photos/400/400?random=10",
            nextDayDelivery = true,
            promoIcon = null,
            listPriceIncVat = 549.0,
            listPriceExVat = 453.72,
            coolbluesChoiceInformationTitle = "Best Seller"
        )
    )
}