package com.example.lazypizza.core.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val TextPrimary = Color(0xFF03131F)
val TestSecondary = Color(0xFF627686)
val TextSecondary08 = Color(0x14627686)
val OnPrimary = Color(0xFFFFFFFF)
val Background = Color(0xFFFAFBFC)
val SurfaceHigher = Color(0xFFFFFFFF)
val SurfaceHighest = Color(0xFFF0F3F6)
val Outline = Color(0xFFE6E7Ed)
val Outline50 = Color(0x80E6E7Ed)
val Primary = Color(0xFFF36B50)
val Primary08 = Color(0x14F36B50)

val ColorScheme.textPrimary: Color
    get() = TextPrimary
val ColorScheme.textSecondary: Color
    get() = TestSecondary
val ColorScheme.textSecondary08: Color
    get() = TextSecondary08
val ColorScheme.outline50: Color
    get() = Outline50
val ColorScheme.primary08: Color
    get() = Primary08

val ColorScheme.primaryGradient: Brush
    get() = Brush.linearGradient(
        colors = listOf(
            Primary,
            Color(0xFFF9966F),
        )
    )
