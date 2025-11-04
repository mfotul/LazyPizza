package com.example.lazypizza.lazypizza.presentation.models

import com.example.lazypizza.lazypizza.domain.pizza.Category
import com.example.lazypizza.lazypizza.presentation.util.toPrice

data class ProductUi(
    val id: String,
    val name: String,
    val price: Double,
    val amount: Int,
    val imagePath: String,
    val category: Category,
    val imageUrl: String? = null
) {
    val formattedPrice = price.toPrice()
    val formattedTotalPrice = (price * amount).toPrice()
}

