package com.seback.moviedbcompose.favs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.ui.common.LoadingContent
import com.seback.moviedbcompose.ui.common.MovieCard

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    onMovieDetails: (Movie) -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val favouriteMovies = viewModel.result.collectAsState().value

    LoadingContent(
        modifier = modifier,
        response = favouriteMovies,
        isEmptyCheck = { it.isEmpty() },
        onRetry = { viewModel.retry() }
    ) { movies ->
        FavouritesMoviesGrid(
            modifier = modifier,
            movies = movies,
            onMovieDetails = onMovieDetails
        )
    }
}

@Composable
fun FavouritesMoviesGrid(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieDetails: (Movie) -> Unit,
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
                showFavIcon = false
            )
        }
    }
}