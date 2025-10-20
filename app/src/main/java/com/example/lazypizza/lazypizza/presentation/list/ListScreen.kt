@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.example.lazypizza.lazypizza.presentation.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.layout.LazyLayoutCacheWindow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.theme.ExtraTypography
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.lazypizza.data.pizza.localDataSource
import com.example.lazypizza.lazypizza.data.pizza.toOtherProductUi
import com.example.lazypizza.lazypizza.data.pizza.toPizzaUi
import com.example.lazypizza.lazypizza.domain.pizza.Category
import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.example.lazypizza.lazypizza.domain.pizza.category
import com.example.lazypizza.lazypizza.domain.pizza.name
import com.example.lazypizza.lazypizza.presentation.list.components.ListCategoryHeader
import com.example.lazypizza.lazypizza.presentation.list.components.ListCategoryRow
import com.example.lazypizza.lazypizza.presentation.list.components.ListOtherProductItem
import com.example.lazypizza.lazypizza.presentation.list.components.ListPizzaItem
import com.example.lazypizza.lazypizza.presentation.list.components.ListSearchField
import com.example.lazypizza.lazypizza.presentation.list.components.ListTopAppBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable

fun ListScreenRoot(
    windowWidthSizeClass: WindowWidthSizeClass,
    onNavigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListScreen(
        windowWidthSizeClass = windowWidthSizeClass,
        products = state.products,
        headersIndexes = state.headersIndices,
        search = state.search,
        onAction = {
            when (it) {
                is ListAction.NavigateToDetail -> onNavigateToDetails(it.id)
                else -> viewModel.onAction(it)
            }
        },
        modifier = modifier
    )
}

@Composable
fun ListScreen(
    windowWidthSizeClass: WindowWidthSizeClass,
    products: Map<Category, List<Product>>,
    headersIndexes: Map<Category, Int>,
    search: String,
    onAction: (ListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val columns = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Expanded -> 2
        else -> 1
    }
    val dpCacheWindow = LazyLayoutCacheWindow(300.dp, 150.dp)
    val lazyGridState = rememberLazyGridState(cacheWindow = dpCacheWindow)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ListTopAppBar(
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets.safeContent,
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        val layoutDirection = LayoutDirection.Ltr
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                start = innerPadding.calculateStartPadding(layoutDirection),
                end = innerPadding.calculateStartPadding(layoutDirection),
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .imePadding()
        ) {
            item(
                span = { GridItemSpan(columns) }
            ) {
                Image(
                    painter = painterResource(R.drawable.pizza),
                    contentDescription = stringResource(R.string.pizza_image),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            item(
                span = { GridItemSpan(columns) }
            ) {
                ListSearchField(
                    value = search,
                    onValueChange = { onAction(ListAction.OnSearchChange(it)) },
                )
            }

            item(
                span = { GridItemSpan(columns) }
            ) {
                ListCategoryRow(
                    onAction = { listAction ->
                        when (listAction) {
                            is ListAction.OnCategoryClick ->
                                headersIndexes[listAction.category]?.let { index ->
                                    coroutineScope.launch {
                                        lazyGridState.animateScrollToItem(index)
                                    }
                                }

                            else -> {}
                        }

                    },
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
            }

            if (products.isEmpty())
                item(
                    span = { GridItemSpan(columns) }
                ) {
                    Text(
                        text = stringResource(R.string.no_results_found_for_your_query),
                        style = ExtraTypography.bodyLargeMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            else
                products.forEach { (category, products) ->
                    stickyHeader {
                        ListCategoryHeader(category = category)
                    }

                    items(products, key = { it.name }) { product ->
                        when (product) {
                            is Product.Pizza ->
                                ListPizzaItem(
                                    pizzaUi = product.toPizzaUi(),
                                    onAction = onAction
                                )

                            is Product.OtherProduct ->
                                ListOtherProductItem(
                                    onAction = onAction,
                                    otherProductUi = product.toOtherProductUi(),
                                )
                        }
                    }
                }

            item(
                span = { GridItemSpan(columns) }
            ) {
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
            }
        }
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    LazyPizzaTheme {
        ListScreen(
            products = localDataSource
                .filter { it.category != Category.EXTRATOPPING }
                .groupBy { it.category },
//            products = emptyMap(),
            headersIndexes = emptyMap(),
            search = "",
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            onAction = {}
        )
    }
}