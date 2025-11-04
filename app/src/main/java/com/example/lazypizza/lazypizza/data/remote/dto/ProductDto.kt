package com.example.lazypizza.lazypizza.data.remote.dto

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
sealed class ProductDto {
    abstract val id: String
    abstract val name: String
    abstract val price: Double
    abstract val imagePath: String
    abstract val imageUrl: String?
    abstract val category: CategoryDto
    abstract val amount: Int
    abstract val productType: String
    abstract val createdAt: Long

    data class PizzaDto(
        override val id: String = "",
        override val name: String = "",
        override val price: Double = 0.0,
        override val imagePath: String = "",
        override val imageUrl: String? = null,
        override val category: CategoryDto = CategoryDto.PIZZA,
        override val amount: Int = 0,
        val shortDescription: String = "",
        val longDescription: String = "",
        val ingredients: List<String> = emptyList(),
        override val productType: String = "",
        override val createdAt: Long = 0
    ) : ProductDto()

    data class OtherProductDto(
        override val id: String = "",
        override val name: String = "",
        override val price: Double = 0.0,
        override val imagePath: String = "",
        override val imageUrl: String? = null,
        override val category: CategoryDto = CategoryDto.DRINK,
        override val amount: Int = 0,
        override val productType: String = "",
        override val createdAt: Long = 0
    ) : ProductDto()
}