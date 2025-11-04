package com.example.lazypizza.lazypizza.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.designsystem.buttons.OutlineFirstButton
import com.example.lazypizza.core.presentation.theme.ExtraTypography
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.lazypizza.presentation.list.components.ListProductPrices
import com.example.lazypizza.lazypizza.presentation.models.ProductUi
import com.example.lazypizza.lazypizza.presentation.preview.PreviewModel

@Composable
fun ProductItem(
    productUi: ProductUi,
    onIncrementClick: (String) -> Unit,
    onDecrementClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    minusDisabled: Boolean = false,
    extraToppings: List<String>? = emptyList(),
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
        Row(
            Modifier.height(IntrinsicSize.Max)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AsyncImage(
                    model = productUi.imageUrl,
                    contentDescription = productUi.name,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxHeight()
                        .padding(
                            12.dp
                        )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = productUi.name,
                            style = ExtraTypography.bodyLargeMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                        )
                        Column {
                            extraToppings?.forEach {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.textSecondary
                                )
                            }
                        }
                    }
                    if (productUi.amount > 0)
                        AmountStepper(
                            value = productUi.amount,
                            onIncrement = {
                                onIncrementClick(productUi.id)
                            },
                            onDecrement = {
                                onDecrementClick(productUi.id)
                            },
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 8.dp),
                            minusDisabled = minusDisabled
                        )
                }
                if (productUi.amount > 0) {
                    IconButton(
                        onClick = {
                            onDeleteClick(productUi.id)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.trash_04),
                            contentDescription = stringResource(R.string.delete),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    ListProductPrices(
                        formattedPrice = productUi.formattedPrice,
                        formattedTotalPrice = productUi.formattedTotalPrice,
                        amount = productUi.amount,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                    )
                } else {
                    Text(
                        text = productUi.formattedPrice,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.textPrimary,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)

                    )
                    OutlineFirstButton(
                        text = stringResource(R.string.add_to_cart),
                        onClick = {
                            onIncrementClick(productUi.id)
                        },
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

        ProductItem(
            PreviewModel.otherProduct.copy(
                amount = 1
            ),
            onDeleteClick = {},
            onIncrementClick = {},
            onDecrementClick = {},
            minusDisabled = true,
            extraToppings = listOf("1 x Pepperoni", "1 x Sausage"),
//                extraToppings = emptyList(),
        )
    }

}