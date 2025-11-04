package com.example.lazypizza.lazypizza.domain.pizza

import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getProducts(search: String? = null): Flow<List<Product>>
    fun getExtraToppings(): Flow<List<Product>>
    fun getProduct(id: String): Flow<Product?>
    fun getCartItems(): Flow<List<Product>>
    fun getCartItem(id: String): Flow<Product?>
    suspend fun updateProductAmount(id: String, amount: Int)
    suspend fun updateCartProductAmount(id: String, amount: Int)
    suspend fun addToCart(id: String): Product?
    suspend fun addPizzaToCart(product: Product)
    suspend fun removeFromCart(id: String)
    suspend fun loadInitialData()
}