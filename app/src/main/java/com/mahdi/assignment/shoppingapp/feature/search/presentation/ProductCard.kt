package com.mahdi.assignment.shoppingapp.feature.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.mahdi.assignment.shoppingapp.feature.search.domain.model.Product
import coil.compose.AsyncImage
import com.mahdi.assignment.shoppingapp.ui.theme.Dimens
import com.mahdi.assignment.shoppingapp.ui.theme.Spacing

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.small),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(modifier = Modifier.padding(Spacing.small)) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.size(Dimens.productImageSize)
            )
            Spacer(modifier = Modifier.width(Spacing.small))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text("€${product.priceIncludingVat}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Rating: ${product.averageRating} (${product.reviewCount} reviews)",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (product.promoText != null) {
                    Text(
                        text = product.promoText,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Red,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
