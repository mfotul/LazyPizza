@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lazypizza.lazypizza.presentation.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.theme.ExtraTypography
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textPrimary

@Composable
fun LazyPizzaTopAppBar(
    text: String,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = text,
                style = ExtraTypography.bodyLargeMedium,
                color = MaterialTheme.colorScheme.textPrimary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}

@Preview
@Composable
fun LazyPizzaTopAppBarPreview() {
    LazyPizzaTheme {
        LazyPizzaTopAppBar(
            text = stringResource(id = R.string.app_name)
        )
    }
}