package com.example.lazypizza.lazypizza.domain.pizza

sealed class Product {
    data class Pizza(
        val id: Int,
        val name: String,
        val shortDescription: String,
        val longDescription: String,
        val price: Double,
        val imagePath: String,
        val imageUrl: String?,
        val category: Category
    ) : Product()

    data class OtherProduct(
        val id: Int,
        val name: String,
        val price: Double,
        val amount: Int,
        val imagePath: String,
        val imageUrl: String?,
        val category: Category
    ) : Product()
}

val Product.category: Category
    get() = when (this) {
        is Product.Pizza -> this.category
        is Product.OtherProduct -> this.category
    }

val Product.name: String
    get() = when (this) {
        is Product.Pizza -> this.name
        is Product.OtherProduct -> this.name
    }

val Product.id: Int
    get() = when (this) {
        is Product.Pizza -> this.id
        is Product.OtherProduct -> this.id
    }

val Product.imagePath: String
    get() = when (this) {
        is Product.Pizza -> this.imagePath
        is Product.OtherProduct -> this.imagePath
    }

val Product.imageUrl: String?
    get() = when (this) {
        is Product.Pizza -> this.imageUrl
        is Product.OtherProduct -> this.imageUrl
    }