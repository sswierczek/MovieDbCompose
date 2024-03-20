package com.seback.moviedbcompose.favs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.ui.common.LoadingContentWithoutResponse
import com.seback.moviedbcompose.ui.common.MoviesGrid

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    onMovieDetails: (Movie) -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val favouriteMovies by viewModel.result.collectAsState()

    LoadingContentWithoutResponse(
        modifier = modifier,
        favouriteMovies,
        isEmptyCheck = { it.isEmpty() }
    ) {
        MoviesGrid(
            modifier = modifier,
            movies = it,
            onMovieDetails = onMovieDetails,
            onFavClick = viewModel::removeFav,
            favs = it // In this case Fav is the same as the list of movies
        )
    }
}
