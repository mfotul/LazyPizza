package com.example.lazypizza.app.navigation.component

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.lazypizza.app.navigation.NavigationRoute
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.primary08
import com.example.lazypizza.core.presentation.theme.textSecondary

@Composable
fun BadgedNavigationItem(
    selected: Boolean,
    destination: NavigationRoute,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    amount: Int? = null,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.textSecondary,
            unselectedIconColor = MaterialTheme.colorScheme.textSecondary,
            unselectedTextColor = MaterialTheme.colorScheme.textSecondary,
            indicatorColor = Color.Transparent
        ),
        icon = {
            BadgedBox(
                badge = {
                    if (amount != null) {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .offset(6.dp, (-6).dp)
                                .dropShadow(
                                    shape = CircleShape,
                                    shadow = Shadow(
                                        radius = 2.dp,
                                        spread = 0.dp,
                                        color = MaterialTheme.colorScheme.primary
                                            .copy(alpha = 0.25f),
                                        offset = DpOffset(0.dp,4.dp)
                                    )
                                )

                        ) {
                            Text(
                                text = amount.toString(),
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier
                                    .padding(1.dp)
                            )
                        }
                    }
                }
            ) {
                Surface(
                    shape = CircleShape,
                    color = if (selected) MaterialTheme.colorScheme.primary08 else Color.Transparent,
                    modifier = Modifier
                        .size(28.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(destination.icon),
                        contentDescription = stringResource(destination.label),
                        modifier = Modifier
                            .padding(6.dp)
                    )
                }
            }
        },
        label = {
            Text(
                text = stringResource(destination.label),
                style = MaterialTheme.typography.titleSmall,
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun LazyPizzaNavItemPreview() {
    LazyPizzaTheme {
        BadgedNavigationItem(
            selected = true,
            destination = NavigationRoute.Cart,
            onClick = {},
            amount = 3
        )
    }
}