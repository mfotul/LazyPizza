package com.example.lazypizza.app.navigation

import androidx.compose.runtime.Immutable

@Immutable
data class NavigationState(
    val cartItemsCount: Int? = null,
)