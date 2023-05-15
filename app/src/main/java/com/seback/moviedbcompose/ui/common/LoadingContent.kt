package com.seback.moviedbcompose.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seback.moviedbcompose.core.data.models.Response


@Composable
fun <T> LoadingContent(
    modifier: Modifier,
    response: Response<T>,
    content: @Composable (T) -> Unit
) {
    when (response) {
        is Response.Loading -> Loading(modifier)
        is Response.Success -> content(response.data)
        is Response.Error -> ErrorDisplay(message = response.message)
    }
}