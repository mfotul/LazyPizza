package com.example.lazypizza.lazypizza.presentation.detail

import androidx.compose.runtime.Immutable
import com.example.lazypizza.lazypizza.presentation.models.PizzaUi
import com.example.lazypizza.lazypizza.presentation.models.ProductUi
import com.example.lazypizza.lazypizza.presentation.util.toPrice

@Immutable
data class DetailState(
    val pizzaUi: PizzaUi? = null,
    val extraToppings: List<ProductUi> = emptyList(),
    val imageURL: String? = null,
    val price: Double = 0.0
) {
    val formatedPrice = price.toPrice()
}
