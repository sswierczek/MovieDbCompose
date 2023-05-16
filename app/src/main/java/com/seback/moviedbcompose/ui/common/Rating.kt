package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seback.moviedbcompose.ui.theme.ratingColor
import com.seback.moviedbcompose.ui.theme.ratingStyle

@Composable
fun Rating(modifier: Modifier, vote: Double) {
    Row(
        modifier = modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Star,
            contentDescription = null,
            tint = ratingColor
        )
        Text(
            text = "$vote",
            style = ratingStyle,
        )
    }
}