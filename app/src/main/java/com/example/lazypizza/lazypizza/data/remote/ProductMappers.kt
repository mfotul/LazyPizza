package com.example.lazypizza.lazypizza.data.remote

import com.example.lazypizza.lazypizza.data.remote.dto.CategoryDto
import com.example.lazypizza.lazypizza.data.remote.dto.ProductDto
import com.example.lazypizza.lazypizza.domain.pizza.Category
import com.example.lazypizza.lazypizza.domain.pizza.Product

fun Product.toDto(): ProductDto {
    return when (this) {
        is Product.Pizza -> ProductDto.PizzaDto(
            id = this.id,
            name = this.name,
            price = this.price,
            imagePath = this.imagePath,
            imageUrl = this.imageUrl,
            category = this.category.toDto(),
            amount = this.amount,
            shortDescription = this.shortDescription,
            longDescription = this.longDescription,
            ingredients = this.ingredients,
            productType = "PIZZA",
            createdAt = System.currentTimeMillis()
        )
        is Product.OtherProduct -> ProductDto.OtherProductDto(
            id = this.id,
            name = this.name,
            price = this.price,
            imagePath = this.imagePath,
            imageUrl = this.imageUrl,
            category = this.category.toDto(),
            amount = this.amount,
            productType = "OTHER_PRODUCT",
            createdAt = System.currentTimeMillis()
        )
    }
}

fun ProductDto.toDomain(): Product {
    return when (this) {
        is ProductDto.PizzaDto -> Product.Pizza(
            id = this.id,
            name = this.name,
            price = this.price,
            imagePath = this.imagePath,
            imageUrl = this.imageUrl,
            category = this.category.toDomain(),
            amount = this.amount,
            shortDescription = this.shortDescription,
            longDescription = this.longDescription,
            ingredients = this.ingredients,
            productType = this.productType,
            createdAt = this.createdAt
        )
        is ProductDto.OtherProductDto -> Product.OtherProduct(
            id = this.id,
            name = this.name,
            price = this.price,
            imagePath = this.imagePath,
            imageUrl = this.imageUrl,
            category = this.category.toDomain(),
            amount = this.amount,
            productType = this.productType,
            createdAt = this.createdAt
        )
    }
}

fun Category.toDto(): CategoryDto {
    return when(this) {
        Category.PIZZA -> CategoryDto.PIZZA
        Category.DRINK -> CategoryDto.DRINK
        Category.ICECREAM -> CategoryDto.ICECREAM
        Category.SAUCE -> CategoryDto.SAUCE
        Category.EXTRATOPPING -> CategoryDto.EXTRATOPPING
    }
}

fun CategoryDto.toDomain(): Category {
    return when(this) {
        CategoryDto.PIZZA -> Category.PIZZA
        CategoryDto.DRINK -> Category.DRINK
        CategoryDto.ICECREAM -> Category.ICECREAM
        CategoryDto.SAUCE -> Category.SAUCE
        CategoryDto.EXTRATOPPING -> Category.EXTRATOPPING
    }
}

