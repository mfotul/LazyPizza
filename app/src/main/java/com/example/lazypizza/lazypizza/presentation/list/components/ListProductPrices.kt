package com.example.lazypizza.lazypizza.presentation.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary

@Composable
fun ListProductPrices(
    formattedPrice: String,
    formattedTotalPrice: String,
    amount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        Text(
            text = formattedTotalPrice,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.textPrimary,
        )
        Text(
            text = "$amount x $formattedPrice",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.textSecondary,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ListProductPricesPreview() {
    LazyPizzaTheme {
        ListProductPrices(
            formattedPrice = "$10.00",
            formattedTotalPrice = "$20.00",
            amount = 2
        )
    }
}