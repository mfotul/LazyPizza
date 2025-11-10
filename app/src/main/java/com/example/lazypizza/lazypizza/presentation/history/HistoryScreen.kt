package com.example.lazypizza.lazypizza.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lazypizza.R
import com.example.lazypizza.app.navigation.NavigationRoute
import com.example.lazypizza.app.navigation.component.LazyNavigationBar
import com.example.lazypizza.app.navigation.component.LazyNavigationRail
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.lazypizza.presentation.components.FilledPrimaryButton
import com.example.lazypizza.lazypizza.presentation.components.LazyPizzaTopAppBar

@Composable
fun HistoryScreen(
    useNavigationalRail: Boolean,
    cartItemsCount: Int?,
    navController: NavController,
    topLevelRoutes: List<NavigationRoute>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            LazyPizzaTopAppBar(
                text = stringResource(R.string.order_history)
            )
        },
        bottomBar = {
            LazyNavigationBar(
                useNavigationalRail = useNavigationalRail,
                cartItemsCount = cartItemsCount,
                navController = navController,
                topLevelRoutes = topLevelRoutes,
            )
        },
        contentWindowInsets = WindowInsets.safeContent,
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyNavigationRail(
                useNavigationalRail = useNavigationalRail,
                cartItemsCount = cartItemsCount,
                navController = navController,
                topLevelRoutes = topLevelRoutes,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.not_signed_in),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.textPrimary
                    )
                    Text(
                        text = stringResource(R.string.please_sign_in_to_view_your_order_history),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.textSecondary
                    )
                }
                FilledPrimaryButton(
                    text = stringResource(R.string.sign_in),
                    onClick = { }
                )
                Spacer(modifier = Modifier.weight(2f))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun HistoryScreenPreview() {
    LazyPizzaTheme {
        HistoryScreen(
            cartItemsCount = 0,
            navController = NavController(LocalContext.current),
            topLevelRoutes = listOf(),
            useNavigationalRail = false
        )
    }
}