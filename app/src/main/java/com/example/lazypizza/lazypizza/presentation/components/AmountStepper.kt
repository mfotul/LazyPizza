package com.example.lazypizza.lazypizza.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.designsystem.buttons.OutlinedItemButton
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary

@Composable
fun AmountStepper(
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    minusDisabled: Boolean = false
) {
    val enabled = !(minusDisabled && value == 1)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .width(96.dp)
    ) {
        OutlinedItemButton(
            icon = R.drawable.minus,
            contentColor = MaterialTheme.colorScheme.textSecondary,
            onClick = onDecrement,
            enabled = enabled
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.textPrimary
        )
        OutlinedItemButton(
            icon = R.drawable.plus,
            contentColor = MaterialTheme.colorScheme.textSecondary,
            onClick = onIncrement
        )
    }
}



@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun AmountStepperPreview() {
    LazyPizzaTheme {
        AmountStepper(
            value = 1,
            onIncrement = {},
            onDecrement = {},
            minusDisabled = true
        )
    }
}

