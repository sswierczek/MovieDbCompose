package com.seback.moviedbcompose.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme
import kotlinx.datetime.LocalDate

@Composable
fun FavouriteButton(
    modifier: Modifier = Modifier,
    movie: Movie,
    isFav: Boolean,
    onFavClick: (Movie) -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onFavClick(movie)
        }
    ) {
        Icon(
            modifier = Modifier.alpha(0.8f),
            tint = if (isFav) Color.Red else MaterialTheme.colors.primary,
            imageVector = Icons.Default.Favorite, contentDescription = null
        )
    }
}

@Preview
@Composable
fun FavPreview() {
    MovieDbComposeTheme {
        FavouriteButton(movie = Movie(
            id = 0, title = "",
            voteAverage = 0.0,
            releaseDate = LocalDate.parse("1999-01-01")
        ),
            isFav = true,
            onFavClick = {})
    }
}