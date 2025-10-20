package com.example.lazypizza.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    background = Background,
    surface = SurfaceHigher,
    surfaceVariant = SurfaceHighest,
    outline = Outline,
)

val LocalExtraTypography = staticCompositionLocalOf { ExtraTypography }

@Composable
fun LazyPizzaTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalExtraTypography provides ExtraTypography
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = Typography,
            content = content
        )
    }
}