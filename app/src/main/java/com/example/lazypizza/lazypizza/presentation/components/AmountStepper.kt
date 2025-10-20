package com.example.lazypizza.lazypizza.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.outline50
import com.example.lazypizza.core.presentation.theme.textPrimary

@Composable
fun AmountStepper(
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .width(96.dp)
    ) {
        StepperButton(
            icon = R.drawable.minus,
            onClick = onDecrement
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.textPrimary
        )
        StepperButton(
            icon = R.drawable.plus,
            onClick = onIncrement
        )
    }
}

@Composable
fun StepperButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedIconButton (
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline50
        ),
        modifier = modifier
            .size(22.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.increment),
            modifier = Modifier.size(14.dp)
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
            onDecrement = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun StepperButtonPreview() {
    LazyPizzaTheme {
        StepperButton(
            icon = R.drawable.plus,
            onClick = {}
        )
    }
}