package com.example.lazypizza.lazypizza.presentation.list.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.theme.ExtraTypography
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.lazypizza.domain.pizza.Category

@Composable
fun ListCategoryHeader(
    category: Category,
    modifier: Modifier = Modifier
) {
    val text = stringResource(
        when (category) {
            Category.PIZZA -> R.string.pizza
            Category.DRINK -> R.string.drinks
            Category.SAUCE -> R.string.sauces
            Category.ICECREAM -> R.string.icecream
            Category.EXTRATOPPING -> R.string.extra_toppings
        }
    ).uppercase()

    Text(
        text = text,
        style = ExtraTypography.bodySmallMedium,
        color = MaterialTheme.colorScheme.textSecondary,
        modifier = modifier
    )

}