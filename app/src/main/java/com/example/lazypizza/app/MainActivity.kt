@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.example.lazypizza.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.lazypizza.app.navigation.NavigationRoot
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            LazyPizzaTheme {
                val windowSize = calculateWindowSizeClass(this)

                NavigationRoot(
                    windowWidthSizeClass = windowSize.widthSizeClass,
                    navController = rememberNavController()
                )
            }
        }
    }
}