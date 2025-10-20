package com.example.lazypizza.lazypizza.domain.pizza

import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getProducts(search: String? = null): Flow<List<Product>>
    fun getExtraToppings(): Flow<List<Product.OtherProduct>>
    fun getProduct(id: Int): Flow<Product?>
    fun updateProductAmount(id: Int, amount: Int)
    suspend fun loadImagesUrl()
}