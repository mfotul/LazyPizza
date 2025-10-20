package com.example.lazypizza.lazypizza.presentation.list

import androidx.compose.runtime.Immutable
import com.example.lazypizza.lazypizza.domain.pizza.Category
import com.example.lazypizza.lazypizza.domain.pizza.Product

@Immutable
data class ListState(
    val isLoading: Boolean = false,
    val products: Map<Category, List<Product>> = emptyMap(),
    val headersIndices: Map<Category, Int> = emptyMap(),
    val search: String = ""
)
