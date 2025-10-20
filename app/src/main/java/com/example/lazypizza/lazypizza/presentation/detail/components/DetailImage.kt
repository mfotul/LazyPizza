package com.example.lazypizza.lazypizza.presentation.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun DetailImage(
    imageUrl: String?,
    description: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}