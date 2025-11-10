package com.example.lazypizza.lazypizza.presentation.cart

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.lazypizza.R
import com.example.lazypizza.app.navigation.NavigationRoute
import com.example.lazypizza.app.navigation.component.LazyNavigationBar
import com.example.lazypizza.app.navigation.component.LazyNavigationRail
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.util.ObserveAsEvents
import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.example.lazypizza.lazypizza.presentation.cart.components.CartContent
import com.example.lazypizza.lazypizza.presentation.cart.components.EmptyCart
import com.example.lazypizza.lazypizza.presentation.components.LazyPizzaTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreenRoot(
    useNavigationalRail: Boolean,
    cartItemsCount: Int?,
    navController: NavController,
    topLevelRoutes: List<NavigationRoute>,
    windowWidthSizeClass: WindowWidthSizeClass,
    onBackClick: () -> Unit,
    viewModel: CartViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val hapticFeedback = LocalHapticFeedback.current

    ObserveAsEvents(viewModel.events) {
        when (it) {
            is CartEvent.OnAddItemToCart -> hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
        }
    }

    CartScreen(
        useNavigationalRail = useNavigationalRail,
        cartItemsCount = cartItemsCount,
        navController = navController,
        topLevelRoutes = topLevelRoutes,
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
    useNavigationalRail: Boolean,
    cartItemsCount: Int?,
    navController: NavController,
    topLevelRoutes: List<NavigationRoute>,
    windowWidthSizeClass: WindowWidthSizeClass,
    products: List<Product>,
    recommendedProducts: List<Product>,
    totalPrice: String,
    onAction: (CartAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            LazyPizzaTopAppBar(
                text = stringResource(R.string.cart)
            )
        },
        bottomBar = {
            LazyNavigationBar(
                useNavigationalRail = useNavigationalRail,
                cartItemsCount = cartItemsCount,
                navController = navController,
                topLevelRoutes = topLevelRoutes,
            )
        },
        contentWindowInsets = WindowInsets.safeContent,
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyNavigationRail(
                useNavigationalRail = useNavigationalRail,
                cartItemsCount = cartItemsCount,
                navController = navController,
                topLevelRoutes = topLevelRoutes,
            )
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
}

@Preview
@Composable
fun CartScreenPreview() {
    LazyPizzaTheme {
        CartScreen(
            useNavigationalRail = false,
            cartItemsCount = null,
            navController = NavController(LocalContext.current),
            topLevelRoutes = emptyList(),
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            products = emptyList(),
            recommendedProducts = emptyList(),
            totalPrice = "",
            onAction = {}
        )
    }
}
