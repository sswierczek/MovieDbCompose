package com.seback.moviedbcompose.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme

@Composable
fun FavouriteButton(
    modifier: Modifier = Modifier,
    movieId: Int,
    isFav: Boolean,
    onFavClick: (Int) -> Unit
) {
    // Introduced to provide smooth UI and instant recomposition when user click to show fav state
    val isFavLocal = remember { mutableStateOf(isFav) }
    IconButton(
        modifier = modifier,
        onClick = {
            isFavLocal.value = !isFavLocal.value
            onFavClick(movieId)
        }
    ) {
        Icon(
            modifier = Modifier.alpha(0.8f),
            tint = if (isFavLocal.value) Color.Red else MaterialTheme.colors.primary,
            imageVector = Icons.Default.Favorite, contentDescription = null
        )
    }
}

@Preview
@Composable
fun FavPreview() {
    MovieDbComposeTheme {
        FavouriteButton(movieId = 0, isFav = true, onFavClick = {})
    }
}