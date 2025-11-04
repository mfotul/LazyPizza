package com.example.lazypizza.lazypizza.presentation.detail

import com.example.lazypizza.lazypizza.presentation.list.ListAction

sealed interface DetailAction {
    data object OnNavigateBack: DetailAction
    data class OnIncrementClick(val id: String) : DetailAction
    data class OnDecrementClick(val id: String) : DetailAction
    data object OnAddToCartClick: DetailAction
}