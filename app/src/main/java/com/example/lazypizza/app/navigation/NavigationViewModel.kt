package com.example.lazypizza.app.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.lazypizza.domain.pizza.DataSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NavigationViewModel(
    private val dataSource: DataSource,
) : ViewModel() {

    val state = dataSource.getCartItems()
        .map { cartItems ->
            NavigationState(cartItemsCount = cartItems.sumOf { it.amount }.takeIf { it > 0 })
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NavigationState()
        )
}