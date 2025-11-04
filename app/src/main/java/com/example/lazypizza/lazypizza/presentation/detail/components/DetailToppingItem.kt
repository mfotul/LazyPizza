package com.example.lazypizza.lazypizza.presentation.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.primary08
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.lazypizza.presentation.components.AmountStepper
import com.example.lazypizza.lazypizza.presentation.detail.DetailAction
import com.example.lazypizza.lazypizza.presentation.models.ProductUi
import com.example.lazypizza.lazypizza.presentation.preview.PreviewModel

@Composable
fun DetailToppingItem(
    extraTopping: ProductUi,
    onAction: (DetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderStrokeColor = if (extraTopping.amount == 0)
        MaterialTheme.colorScheme.outline
    else
        MaterialTheme.colorScheme.primary

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderStrokeColor
        ),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary08,
                modifier = Modifier
                    .size(56.dp)
            ) {
                AsyncImage(
                    model = extraTopping.imageUrl,
                    contentDescription = extraTopping.name,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Text(
                text = extraTopping.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.textSecondary,
            )
            if (extraTopping.amount != 0)
                AmountStepper(
                    value = extraTopping.amount,
                    onIncrement = { onAction(DetailAction.OnIncrementClick(extraTopping.id)) },
                    onDecrement = { onAction(DetailAction.OnDecrementClick(extraTopping.id)) },
                )
            else
                Text(
                    text = extraTopping.formattedPrice,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.textPrimary,
                )
        }
    }
}

@Preview
@Composable
fun DetailToppingItemPreview() {
    LazyPizzaTheme {
        DetailToppingItem(
            extraTopping = PreviewModel.otherProduct.copy(amount = 1),
            onAction = {}
        )
    }
}