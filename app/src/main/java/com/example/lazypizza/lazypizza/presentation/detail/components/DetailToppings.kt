package com.example.lazypizza.lazypizza.presentation.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.theme.ExtraTypography
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.lazypizza.presentation.detail.DetailAction
import com.example.lazypizza.lazypizza.presentation.models.ProductUi

fun LazyListScope.detailToppings(
    extraToppings: List<ProductUi>,
    onAction: (DetailAction) -> Unit,
) {
    item {
        Text(
            text = stringResource(R.string.add_extra_toppings),
            style = ExtraTypography.bodySmallMedium,
            color = MaterialTheme.colorScheme.textSecondary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
    items(extraToppings.chunked(3)) { extraToppingRow ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            extraToppingRow.forEach { extraTopping ->
                DetailToppingItem(
                    extraTopping = extraTopping,
                    onAction = onAction,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            onClick = {
                                onAction(DetailAction.OnIncrementClick(extraTopping.id))
                            }
                        )


                )
            }
        }

    }
    item {
        Spacer(modifier = Modifier.height(48.dp))
    }
}