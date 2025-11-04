package com.example.lazypizza.lazypizza.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.theme.ExtraTypography
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.lazypizza.data.pizza.toProductUi
import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.example.lazypizza.lazypizza.presentation.cart.CartAction
import com.example.lazypizza.lazypizza.presentation.components.FilledPrimaryButton

@Composable
fun CartAddOns(
    recommendedProducts: List<Product>,
    onAction: (CartAction) -> Unit,
    totalPrice: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.recommended_to_add_to_your_order),
            style = ExtraTypography.bodySmallMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.textSecondary
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .height(202.dp)
                .fillMaxWidth()
        ) {
            items(items = recommendedProducts, key = { it.id }) {
                RecommendedItem(
                    productUi = it.toProductUi(),
                    onClick = { onAction(CartAction.OnAddToCartClick(it.id)) }
                )
            }
        }
        FilledPrimaryButton(
            text = stringResource(R.string.proceed_to_checkout, totalPrice),
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}