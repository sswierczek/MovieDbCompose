package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.secondary
        ),
        onClick = { onClick() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(color = MaterialTheme.colors.onSecondary, text = text)
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = text,
                tint = MaterialTheme.colors.onSecondary
            )
        }
    }
}

@Composable
@Preview
fun FilterButtonPreview() {
    MovieDbComposeTheme {
        FilterButton(text = "Filter", onClick = {}, modifier = Modifier)
    }
}