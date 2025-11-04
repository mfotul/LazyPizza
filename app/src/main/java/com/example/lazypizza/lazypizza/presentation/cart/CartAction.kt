package com.example.lazypizza.lazypizza.presentation.cart

sealed interface CartAction {
    data object OnBackClick : CartAction
    data class OnIncrementClick(val id: String) : CartAction
    data class OnDecrementClick(val id: String) : CartAction
    data class OnDeleteClick(val id: String) : CartAction
    data class OnAddToCartClick(val id: String) : CartAction
}