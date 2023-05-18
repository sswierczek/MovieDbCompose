package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme

@Composable
fun LoadingContentLazy(
    modifier: Modifier,
    response: LazyPagingItems<*>,
    onRetry: () -> Unit,
) {
    when (response.loadState.refresh) {
        is LoadState.Loading -> Loading(modifier)
        is LoadState.Error -> ErrorDisplay(modifier, message = "", onRetry = onRetry)
        else -> {}
    }
}

@Composable
fun <T> LoadingContent(
    modifier: Modifier,
    response: Response<T>,
    onRetry: () -> Unit,
    isEmptyCheck: (T) -> Boolean,
    content: @Composable (T) -> Unit
) {
    when (response) {
        is Response.Loading -> Loading(modifier)
        is Response.Success -> if (isEmptyCheck(response.data)) EmptyBody(modifier) else content(
            response.data
        )

        is Response.Error -> ErrorDisplay(modifier, message = response.message, onRetry = onRetry)
    }
}

@Preview
@Composable
fun LoadingContentPreviewLoading() {
    MovieDbComposeTheme {
        Scaffold {
            LoadingContent(
                modifier = Modifier.padding(it),
                Response.Loading("Loading"),
                { },
                { false }
            ) {}
        }
    }
}

@Preview
@Composable
fun LoadingContentPreviewError() {
    MovieDbComposeTheme {
        Scaffold {
            LoadingContent(
                modifier = Modifier.padding(it),
                Response.Error<String>("Error loading content", Throwable("Error 500")),
                {},
                { false }
            ) {}
        }
    }
}