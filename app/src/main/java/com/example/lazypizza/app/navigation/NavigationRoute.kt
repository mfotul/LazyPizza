package com.example.lazypizza.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object List : NavigationRoute

    @Serializable
    data class Detail(val id: Int) : NavigationRoute
}