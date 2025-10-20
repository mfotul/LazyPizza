package com.example.lazypizza.lazypizza.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.lazypizza.presentation.list.ListAction
import com.example.lazypizza.lazypizza.domain.pizza.Category

@Composable
fun ListCategoryRow(
    onAction: (ListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        ListCategoryItem(
            text = stringResource(R.string.pizza),
            onClick = {
                onAction(ListAction.OnCategoryClick(Category.PIZZA))
            }
        )
        ListCategoryItem(
            text = stringResource(R.string.drinks),
            onClick = {
                onAction(ListAction.OnCategoryClick(Category.DRINK))
            }
        )
        ListCategoryItem(
            text = stringResource(R.string.sauces),
            onClick = {
                onAction(ListAction.OnCategoryClick(Category.SAUCE))
            }
        )
        ListCategoryItem(
            text = stringResource(R.string.icecream),
            onClick = {
                onAction(ListAction.OnCategoryClick(Category.ICECREAM))
            }
        )
    }
}

@Preview
@Composable
fun ListCategoryRowPreview() {
    LazyPizzaTheme {
        ListCategoryRow(
            {}
        )
    }
}