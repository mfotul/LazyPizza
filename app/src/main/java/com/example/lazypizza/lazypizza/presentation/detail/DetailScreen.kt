@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lazypizza.lazypizza.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.lazypizza.data.pizza.localDataSource
import com.example.lazypizza.lazypizza.data.pizza.toOtherProductUi
import com.example.lazypizza.lazypizza.domain.pizza.Category
import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.example.lazypizza.lazypizza.domain.pizza.category
import com.example.lazypizza.lazypizza.presentation.detail.components.DetailBottomBar
import com.example.lazypizza.lazypizza.presentation.detail.components.DetailImage
import com.example.lazypizza.lazypizza.presentation.detail.components.DetailNameDescription
import com.example.lazypizza.lazypizza.presentation.detail.components.DetailTopBar
import com.example.lazypizza.lazypizza.presentation.detail.components.detailToppings
import com.example.lazypizza.lazypizza.presentation.models.OtherProductUi
import com.example.lazypizza.lazypizza.presentation.models.PizzaUi
import com.example.lazypizza.lazypizza.presentation.preview.PreviewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreenRoot(
    onNavigateBack: () -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
    viewmodel: DetailViewModel = koinViewModel(),
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

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
    extraToppings: List<OtherProductUi>,
    windowWidthSizeClass: WindowWidthSizeClass,
    onAction: (DetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    val isExpanded = windowWidthSizeClass == WindowWidthSizeClass.Expanded

    Scaffold(
        topBar = {
            DetailTopBar(
                navigateBack = { onAction(DetailAction.OnNavigateBack) }
            )
        },
        contentWindowInsets = WindowInsets.safeContent,
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        val layoutDirection = LayoutDirection.Ltr
        val paddings = PaddingValues(
            top = innerPadding.calculateTopPadding(),
            start = innerPadding.calculateStartPadding(layoutDirection),
            end = innerPadding.calculateStartPadding(layoutDirection),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isExpanded) innerPadding else paddings)
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
                    ){
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
                        DetailBottomBar(
                            price = "0",
                            onClick = {},
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
                    DetailBottomBar(
                        price = formatedPrice,
                        onClick = {},
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
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
            .map { (it as Product.OtherProduct).toOtherProductUi() }
        DetailScreen(
            pizzaUi = PreviewModel.pizza,
            imageUrl = "",
            formatedPrice = "$1.00",
            extraToppings = toppings,
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            onAction = {}
        )
    }
}
