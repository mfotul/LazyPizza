package com.example.lazypizza.lazypizza.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.lazypizza.data.pizza.localDataSource
import com.example.lazypizza.lazypizza.data.pizza.toProductUi
import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.example.lazypizza.lazypizza.presentation.cart.CartAction
import com.example.lazypizza.lazypizza.presentation.components.ProductItem

@Composable
fun CartContent(
    windowWidthSizeClass: WindowWidthSizeClass,
    products: List<Product>,
    recommendedProducts: List<Product>,
    totalPrice: String,
    onAction: (CartAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = modifier
                .weight(1f)
        ) {
            items(items = products, key = { it.id }) { product ->
                ProductItem(
                    productUi = product.toProductUi(),
                    onDeleteClick = {
                        onAction(CartAction.OnDeleteClick(it))
                    },
                    onIncrementClick = {
                        onAction(CartAction.OnIncrementClick(it))
                    },
                    onDecrementClick = {
                        onAction(CartAction.OnDecrementClick(it))
                    },
                    minusDisabled = true,
                    extraToppings = (product as? Product.Pizza)?.ingredients
                )
            }
            if (windowWidthSizeClass != WindowWidthSizeClass.Expanded)
                item {
                    CartAddOns(
                        windowWidthSizeClass = windowWidthSizeClass,
                        recommendedProducts = recommendedProducts,
                        onAction = onAction,
                        totalPrice = totalPrice
                    )
                }
        }
        if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
            CartAddOns(
                windowWidthSizeClass = windowWidthSizeClass,
                recommendedProducts = recommendedProducts,
                onAction = onAction,
                totalPrice = totalPrice,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 1500)
@Composable
fun CartContentPreview() {
    LazyPizzaTheme {
        val products = buildList {
            add(localDataSource[1])
            add(localDataSource[2])
            add(localDataSource[10])
        }
        CartContent(
            windowWidthSizeClass = WindowWidthSizeClass.Expanded,
            products = products,
            recommendedProducts = localDataSource.slice(10..14),
            totalPrice = "$10.12",
            onAction = {}
        )
    }
}