package com.example.lazypizza.lazypizza.presentation.cart

import androidx.compose.runtime.Immutable
import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.example.lazypizza.lazypizza.presentation.util.toPrice

@Immutable
data class CartState(
    val products: List<Product> = emptyList(),
    val recommendedProducts: List<Product> = emptyList(),
    val totalPrice: Double = 0.0
) {
    val formattedTotalPrice: String = totalPrice.toPrice()
}
