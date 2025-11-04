package com.example.lazypizza.core.presentation.designsystem.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.outline50

@Composable
fun OutlinedItemButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentColor: Color = LocalContentColor.current
) {
    OutlinedIconButton (
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline50
        ),
        colors = IconButtonDefaults.outlinedIconButtonColors(
            contentColor = contentColor,
            disabledContentColor = MaterialTheme.colorScheme.outline50
        ),
        modifier = modifier
            .size(22.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.increment),
            modifier = Modifier.size(14.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun OutlinedItemButtonPreview() {
    LazyPizzaTheme {
        OutlinedItemButton(
            icon = R.drawable.plus,
            enabled = true,
            onClick = {}
        )
    }
}