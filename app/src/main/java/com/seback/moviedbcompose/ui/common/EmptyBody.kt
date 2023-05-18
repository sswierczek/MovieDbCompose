package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seback.moviedbcompose.R
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme

@Composable
fun EmptyBody(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.HeartBroken, contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .alpha(0.5f)
                .size(130.dp)
                .padding(16.dp)
        )
        Text(
            modifier = Modifier
                .paddingFromBaseline(bottom = 16.dp)
                .alpha(0.5f),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview
@Composable
fun EmptyBodyPreview() {
    MovieDbComposeTheme {
        Scaffold {
            EmptyBody(modifier = Modifier.padding(it))
        }
    }
}