package com.example.lazypizza.lazypizza.presentation.models

import com.example.lazypizza.lazypizza.presentation.util.toPrice

data class PizzaUi(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val longDescription: String,
    val price: Double,
    val imagePath: String,
    val imageUrl: String? = null,
) {
    val formattedPrice = price.toPrice()
}