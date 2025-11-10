package com.example.lazypizza.app.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lazypizza.app.navigation.NavigationRoute
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textPrimary

@Composable
fun LazyNavigationBar(
    useNavigationalRail: Boolean,
    cartItemsCount: Int?,
    topLevelRoutes: List<NavigationRoute>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    if (!useNavigationalRail) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val selectedRoute = navBackStackEntry?.destination?.route?.split(".")?.last()

        NavigationBar(
            windowInsets = NavigationBarDefaults.windowInsets,
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = modifier
                .fillMaxWidth()
        ) {
            val currentRoute = if (selectedRoute == "List") "Menu" else selectedRoute

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .dropShadow(
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp
                        ),
                        shadow = Shadow(
                            radius = 16.dp,
                            color = MaterialTheme.colorScheme.textPrimary.copy(
                                alpha = 0.06f
                            ),
                            offset = DpOffset(0.dp, (-4).dp)
                        )
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp
                        )
                    )
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp)
                        .padding(top = 8.dp)
                ) {
                    topLevelRoutes.forEach { destination ->
                        val amount =  if (destination == NavigationRoute.Cart) cartItemsCount else null
                        BadgedNavigationBarItem(
                            selected = destination.toString() == currentRoute,
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
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F3F6)
@Composable
fun LazyNavigationBarPreview() {
    LazyPizzaTheme {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyNavigationBar(
                useNavigationalRail = false,
                cartItemsCount = 1,
                topLevelRoutes = listOf(
                    NavigationRoute.Menu,
                    NavigationRoute.Cart,
                    NavigationRoute.History
                ),
                navController = NavController(LocalContext.current)
            )
        }
    }
}