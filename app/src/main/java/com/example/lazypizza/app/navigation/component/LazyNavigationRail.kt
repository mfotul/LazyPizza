package com.example.lazypizza.app.navigation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lazypizza.app.navigation.NavigationRoute
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme

@Composable
fun LazyNavigationRail(
    useNavigationalRail: Boolean,
    topLevelRoutes: List<NavigationRoute>,
    cartItemsCount: Int?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    if (useNavigationalRail) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val selectedRoute = navBackStackEntry?.destination?.route?.split(".")?.last()

        NavigationRail(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            topLevelRoutes.forEach { destination ->
                val amount = if (destination == NavigationRoute.Cart) cartItemsCount else null
                BadgedNavigationItem(
                    selected = destination.toString() == selectedRoute,
                    destination = destination,
                    onClick = {
                        navController.navigate(route = destination) {
                            popUpTo(navController.graph.id) {
                                saveState = true
                                inclusive = true
                            }
                            restoreState = true
                        }
                    },
                    amount = amount,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        VerticalDivider(
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Preview
@Composable
fun LazyNavigationRailPreview() {
    LazyPizzaTheme {
        LazyNavigationRail(
            useNavigationalRail = true,
            topLevelRoutes = listOf(
                NavigationRoute.Menu,
                NavigationRoute.Cart,
                NavigationRoute.History
            ),
            cartItemsCount = 5,
            navController = NavController(LocalContext.current)
        )
    }
}