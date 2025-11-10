@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lazypizza.app.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.lazypizza.lazypizza.presentation.cart.CartScreenRoot
import com.example.lazypizza.lazypizza.presentation.detail.DetailScreenRoot
import com.example.lazypizza.lazypizza.presentation.history.HistoryScreen
import com.example.lazypizza.lazypizza.presentation.list.ListScreenRoot
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
                    useNavigationalRail = useNavigationalRail,
                    cartItemsCount = state.cartItemsCount,
                    navController = navController,
                    topLevelRoutes = topLevelRoutes,
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
                useNavigationalRail = useNavigationalRail,
                cartItemsCount = state.cartItemsCount,
                navController = navController,
                topLevelRoutes = topLevelRoutes,
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
            HistoryScreen(
                useNavigationalRail = useNavigationalRail,
                cartItemsCount = state.cartItemsCount,
                navController = navController,
                topLevelRoutes = topLevelRoutes,
            )
        }
    }
}
