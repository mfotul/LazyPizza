@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lazypizza.lazypizza.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.util.ObserveAsEvents
import com.example.lazypizza.lazypizza.data.pizza.localDataSource
import com.example.lazypizza.lazypizza.data.pizza.toProductUi
import com.example.lazypizza.lazypizza.domain.pizza.Category
import com.example.lazypizza.lazypizza.presentation.detail.components.DetailCheckoutBar
import com.example.lazypizza.lazypizza.presentation.detail.components.DetailImage
import com.example.lazypizza.lazypizza.presentation.detail.components.DetailNameDescription
import com.example.lazypizza.lazypizza.presentation.detail.components.detailToppings
import com.example.lazypizza.lazypizza.presentation.models.PizzaUi
import com.example.lazypizza.lazypizza.presentation.models.ProductUi
import com.example.lazypizza.lazypizza.presentation.preview.PreviewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreenRoot(
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
    viewmodel: DetailViewModel = koinViewModel(),
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()
    val hapticFeedback = LocalHapticFeedback.current

    ObserveAsEvents(viewmodel.events) {
        when (it) {
            is DetailEvent.OnItemAddedToCart -> {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                onNavigateToCart()
            }
        }
    }

    state.pizzaUi?.let { pizzaUi ->
        DetailScreen(
            pizzaUi = pizzaUi,
            imageUrl = state.imageURL,
            formatedPrice = state.formatedPrice,
            extraToppings = state.extraToppings,
            windowWidthSizeClass = windowWidthSizeClass,
            onAction = {
                when (it) {
                    DetailAction.OnNavigateBack -> onNavigateBack()
                    else -> viewmodel.onAction(it)
                }
            }
        )
    }


}

@Composable
fun DetailScreen(
    pizzaUi: PizzaUi,
    imageUrl: String?,
    formatedPrice: String,
    extraToppings: List<ProductUi>,
    windowWidthSizeClass: WindowWidthSizeClass,
    onAction: (DetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    val isExpanded = windowWidthSizeClass == WindowWidthSizeClass.Expanded

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (isExpanded)
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    DetailImage(
                        imageUrl = imageUrl,
                        description = pizzaUi.name,
                        modifier = Modifier
                            .height(240.dp)
                            .fillMaxWidth()
                    )
                    DetailNameDescription(
                        name = pizzaUi.name,
                        description = pizzaUi.longDescription,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        state = lazyListState,
                        contentPadding = PaddingValues(
                            vertical = 16.dp,
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .dropShadow(
                                shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    bottomStart = 16.dp
                                ),
                                shadow = Shadow(
                                    radius = 16.dp,
                                    spread = 0.dp,
                                    color = MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.04f
                                    ),
                                    offset = DpOffset(0.dp, 4.dp)
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        detailToppings(
                            extraToppings = extraToppings,
                            onAction = onAction
                        )
                    }
                    DetailCheckoutBar(
                        price = formatedPrice,
                        onClick = { onAction(DetailAction.OnAddToCartClick) },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    item {
                        Column {
                            DetailImage(
                                imageUrl = imageUrl,
                                description = pizzaUi.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .clip(
                                        RoundedCornerShape(bottomEnd = 16.dp)
                                    )
                                    .background(MaterialTheme.colorScheme.background)
                            )
                            DetailNameDescription(
                                name = pizzaUi.name,
                                description = pizzaUi.longDescription,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background)
                                    .clip(
                                        RoundedCornerShape(topStart = 16.dp)
                                    )
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(16.dp)
                            )
                        }
                    }
                    detailToppings(
                        extraToppings = extraToppings,
                        onAction = onAction
                    )

                }
                DetailCheckoutBar(
                    price = formatedPrice,
                    onClick = { onAction(DetailAction.OnAddToCartClick) },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}


@Preview(widthDp = 860, heightDp = 640)
@Composable
fun DetailScreenPreview() {
    LazyPizzaTheme {
        val toppings = localDataSource
            .filter { it.category == Category.EXTRATOPPING }
            .map { it.toProductUi() }
        DetailScreen(
            pizzaUi = PreviewModel.pizza,
            imageUrl = "",
            formatedPrice = "$1.00",
            extraToppings = toppings,
            windowWidthSizeClass = WindowWidthSizeClass.Expanded,
            onAction = {}
        )
    }
}
