package com.example.lazypizza.app.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lazypizza.lazypizza.presentation.detail.DetailScreenRoot
import com.example.lazypizza.lazypizza.presentation.list.ListScreenRoot


@Composable
fun NavigationRoot(
    windowWidthSizeClass: WindowWidthSizeClass,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.List,
    ) {
        composable<NavigationRoute.List> {
            ListScreenRoot(
                windowWidthSizeClass = windowWidthSizeClass,
                onNavigateToDetails = {
                    navController.navigate(NavigationRoute.Detail(it))
                }
            )
        }
        composable<NavigationRoute.Detail> { backStackEntry ->
            DetailScreenRoot(
                windowWidthSizeClass = windowWidthSizeClass,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}