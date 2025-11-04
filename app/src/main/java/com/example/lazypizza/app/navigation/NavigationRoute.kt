package com.example.lazypizza.app.navigation

import com.example.lazypizza.R
import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    val label: Int
    val icon: Int

    @Serializable
    data object Menu : NavigationRoute {
        override val label = R.string.menu
        override val icon = R.drawable.book_open_02
    }

    @Serializable
    data object Cart : NavigationRoute {
        override val label = R.string.cart
        override val icon = R.drawable.shopping_cart_02
    }

    @Serializable
    data object History : NavigationRoute {
        override val label = R.string.history
        override val icon = R.drawable.group
    }

    @Serializable
    data object List : NavigationRoute {
        override val label = 0
        override val icon = 0
    }

    @Serializable
    data class Detail(val id: String) : NavigationRoute {
        override val label = 0
        override val icon = 0
    }
}