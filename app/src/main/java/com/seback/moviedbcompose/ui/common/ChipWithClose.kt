package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme

@Composable
fun ChipWithCloseIcon(
    modifier: Modifier = Modifier,
    text: String,
    onCloseClick: () -> Unit
) {
    Surface(
        modifier = modifier.sizeIn(maxHeight = 32.dp),
        color = MaterialTheme.colors.secondaryVariant,
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.body2
            )
            IconButton(onClick = onCloseClick) {
                Icon(Icons.Default.Close, contentDescription = "Close", Modifier.size(16.dp))
            }
        }
    }
}

@Composable
@Preview
fun ChipWithCloseIconPreview() {
    MovieDbComposeTheme {
        ChipWithCloseIcon(modifier = Modifier, text = "Chip", onCloseClick = {})
    }
}