package com.example.lazypizza.lazypizza.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.core.presentation.util.ObserveAsEvents
import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.example.lazypizza.lazypizza.presentation.cart.components.CartContent
import com.example.lazypizza.lazypizza.presentation.cart.components.EmptyCart
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreenRoot(
    windowWidthSizeClass: WindowWidthSizeClass,
    onBackClick: () -> Unit,
    viewModel: CartViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val hapticFeedback = LocalHapticFeedback.current

    ObserveAsEvents(viewModel.event) {
        when (it) {
            is CartEvent.OnAddItemToCart -> hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
        }
    }

    CartScreen(
        windowWidthSizeClass = windowWidthSizeClass,
        products = state.products,
        recommendedProducts = state.recommendedProducts,
        totalPrice = state.formattedTotalPrice,
        onAction = {
            when (it) {
                is CartAction.OnBackClick ->
                    onBackClick()

                else ->
                    viewModel.onAction(it)
            }
        }

    )
}

@Composable
fun CartScreen(
    windowWidthSizeClass: WindowWidthSizeClass,
    products: List<Product>,
    recommendedProducts: List<Product>,
    totalPrice: String,
    onAction: (CartAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier

    ) {
        if (products.isEmpty())
            EmptyCart(
                onClick = {
                    onAction(CartAction.OnBackClick)
                }
            )
        else
            CartContent(
                windowWidthSizeClass = windowWidthSizeClass,
                products = products,
                recommendedProducts = recommendedProducts,
                totalPrice = totalPrice,
                onAction = onAction
            )
    }
}