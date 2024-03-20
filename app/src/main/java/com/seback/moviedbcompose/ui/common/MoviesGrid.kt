package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seback.moviedbcompose.core.data.models.Movie

@Composable
fun MoviesGrid(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieDetails: (Movie) -> Unit,
    favs: List<Movie>,
    onFavClick: (Movie) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(minSize = 150.dp)
    ) {
        items(movies)
        { item ->
            MovieCard(
                modifier = Modifier.clickable {
                    onMovieDetails(item)
                },
                movie = item,
                isFav = favs.contains(item),
                onFavClick = onFavClick
            )
        }
    }
}
