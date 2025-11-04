package com.example.lazypizza.app.navigation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lazypizza.app.navigation.NavigationRoute

@Composable
fun NavigationRailItems(
    topLevelRoutes: List<NavigationRoute>,
    selectedRoute: NavigationRoute,
    cartItemsCount: Int?,
    onClick: (NavigationRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        topLevelRoutes.forEach { destination ->
            val amount =  if (destination == NavigationRoute.Cart) cartItemsCount else null
            BadgedNavigationItem(
                selected = destination == selectedRoute,
                destination = destination,
                onClick = {
                    onClick(destination)
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