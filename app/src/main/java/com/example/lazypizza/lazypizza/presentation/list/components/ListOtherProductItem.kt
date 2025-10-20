package com.example.lazypizza.lazypizza.presentation.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.designsystem.buttons.OutlineFirstButton
import com.example.lazypizza.core.presentation.theme.ExtraTypography
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.lazypizza.presentation.components.AmountStepper
import com.example.lazypizza.lazypizza.presentation.list.ListAction
import com.example.lazypizza.lazypizza.presentation.models.OtherProductUi
import com.example.lazypizza.lazypizza.presentation.preview.PreviewModel

@Composable
fun ListOtherProductItem(
    otherProductUi: OtherProductUi,
    onAction: (ListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Row {
            AsyncImage(
                model = otherProductUi.imageUrl,
                contentDescription = otherProductUi.name,
                modifier = Modifier
                    .size(120.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(4.dp)
            ) {
                Text(
                    text = otherProductUi.name,
                    style = ExtraTypography.bodyLargeMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)

                )
                if (otherProductUi.amount > 0) {
                    IconButton(
                        onClick = { onAction(ListAction.OnDeleteClick(otherProductUi.id)) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.trash_04),
                            contentDescription = stringResource(R.string.delete),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    AmountStepper(
                        value = otherProductUi.amount,
                        onIncrement = { onAction(ListAction.OnIncrementClick(otherProductUi.id)) },
                        onDecrement = { onAction(ListAction.OnDecrementClick(otherProductUi.id)) },
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(
                                start = 12.dp,
                                bottom = 18.dp
                            )
                    )
                    ListProductPrices(
                        formattedPrice = otherProductUi.formattedPrice,
                        formattedTotalPrice = otherProductUi.formattedTotalPrice,
                        amount = otherProductUi.amount,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                    )
                } else {
                    Text(
                        text = otherProductUi.formattedPrice,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.textPrimary,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)

                    )
                    OutlineFirstButton(
                        text = stringResource(R.string.add_to_cart),
                        onClick = { onAction(ListAction.OnIncrementClick(otherProductUi.id)) },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ListOtherProductItemPreview() {
    LazyPizzaTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ListOtherProductItem(
                PreviewModel.otherProduct.copy(
                    amount = 0
                ),
                onAction = {}
            )
        }
    }
}