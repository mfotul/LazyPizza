@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lazypizza.app.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.example.lazypizza.R
import com.example.lazypizza.app.navigation.component.NavigationBarItems
import com.example.lazypizza.app.navigation.component.NavigationRailItems
import com.example.lazypizza.lazypizza.presentation.cart.CartScreenRoot
import com.example.lazypizza.lazypizza.presentation.components.LazyPizzaTopAppBar
import com.example.lazypizza.lazypizza.presentation.detail.DetailScreenRoot
import com.example.lazypizza.lazypizza.presentation.detail.components.DetailTopBar
import com.example.lazypizza.lazypizza.presentation.history.HistoryScreen
import com.example.lazypizza.lazypizza.presentation.list.ListScreenRoot
import com.example.lazypizza.lazypizza.presentation.list.components.ListTopAppBar
import org.koin.androidx.compose.koinViewModel


@Composable
fun NavigationRoot(
    windowWidthSizeClass: WindowWidthSizeClass,
    navController: NavHostController,
    viewModel: NavigationViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val useNavigationalRail = windowWidthSizeClass == WindowWidthSizeClass.Expanded
    val topLevelRoutes = listOf(NavigationRoute.Menu, NavigationRoute.Cart, NavigationRoute.History)
    var selectedRoute by remember { mutableStateOf<NavigationRoute>(NavigationRoute.Menu) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("/")
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            when (currentRoute) {
                NavigationRoute.List::class.qualifiedName ->
                    ListTopAppBar(
                        scrollBehavior = scrollBehavior
                    )

                NavigationRoute.Detail::class.qualifiedName -> {
                    DetailTopBar(
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }

                NavigationRoute.Cart::class.qualifiedName -> {
                    LazyPizzaTopAppBar(
                        text = stringResource(R.string.cart)
                    )
                }

                NavigationRoute.History::class.qualifiedName -> {
                    LazyPizzaTopAppBar(
                        text = stringResource(R.string.order_history)
                    )
                }

                else -> {}

            }
        },
        bottomBar = {
            if (!useNavigationalRail && currentRoute != NavigationRoute.Detail::class.qualifiedName)
                NavigationBar(
                    windowInsets = NavigationBarDefaults.windowInsets,
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    NavigationBarItems(
                        topLevelRoutes = topLevelRoutes,
                        selectedRoute = selectedRoute,
                        cartItemsCount = state.cartItemCount,
                        onClick = { destination ->
                            navController.navigate(route = destination) {
                                popUpTo(navController.graph.id) {
                                    saveState = true
                                    inclusive = true
                                }
                                restoreState = true
                            }
                        }
                    )
                }
        },
        contentWindowInsets = WindowInsets.safeContent,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (useNavigationalRail && currentRoute != NavigationRoute.Detail::class.qualifiedName) {
                NavigationRailItems(
                    topLevelRoutes = topLevelRoutes,
                    selectedRoute = selectedRoute,
                    cartItemsCount = state.cartItemCount,
                    onClick = { destination ->
                        navController.navigate(route = destination) {
                            popUpTo(navController.graph.id) {
                                saveState = true
                                inclusive = true
                            }
                            restoreState = true
                        }
                        selectedRoute = destination
                    }
                )
            }

            NavHost(
                navController = navController,
                startDestination = NavigationRoute.Menu,
                modifier = Modifier
            ) {
                navigation<NavigationRoute.Menu>(
                    startDestination = NavigationRoute.List
                ) {
                    composable<NavigationRoute.List> {
                        ListScreenRoot(
                            windowWidthSizeClass = windowWidthSizeClass,
                            onNavigateToDetails = {
                                navController.navigate(NavigationRoute.Detail(it))
                            }
                        )
                    }
                    composable<NavigationRoute.Detail> {
                        DetailScreenRoot(
                            windowWidthSizeClass = windowWidthSizeClass,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onNavigateToCart = {
                                navController.navigate(NavigationRoute.Menu) {
                                    popUpTo(navController.graph.id) {
                                        saveState = true
                                        inclusive = true
                                    }
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
                composable<NavigationRoute.Cart> {
                    CartScreenRoot(
                        windowWidthSizeClass = windowWidthSizeClass,
                        onBackClick = {
                            navController.navigate(NavigationRoute.Menu) {
                                popUpTo(navController.graph.id) {
                                    saveState = true
                                    inclusive = true
                                }
                                restoreState = true
                            }
                        }
                    )
                }
                composable<NavigationRoute.History> {
                    HistoryScreen()
                }
            }
        }
    }
}
