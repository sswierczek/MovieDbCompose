package com.seback.moviedbcompose.latest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.ui.common.LoadingContentLazy
import com.seback.moviedbcompose.ui.common.MovieCard
import timber.log.Timber

@Composable
fun DiscoverLatestScreen(
    modifier: Modifier = Modifier,
    onMovieDetails: (Movie) -> Unit,
    discoverLatestViewModel: DiscoverLatestViewModel = hiltViewModel()
) {
    val moviesLazy = discoverLatestViewModel.moviesPager.collectAsLazyPagingItems()

    val favs by discoverLatestViewModel.favs.collectAsState()

    LoadingContentLazy(modifier = modifier, response = moviesLazy, onRetry = {
        moviesLazy.retry()
    })
    DiscoverMoviesGrid(
        modifier = modifier,
        movies = moviesLazy,
        onMovieDetails = onMovieDetails,
        favs = favs,
        onFavClick = { movie ->
            discoverLatestViewModel.favSwitch(movie)
        })
}

@Composable
fun DiscoverMoviesGrid(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    onMovieDetails: (Movie) -> Unit,
    favs: List<Movie>,
    onFavClick: (Movie) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(minSize = 150.dp)
    ) {
        items(movies.itemCount)
        { index ->
            movies[index]?.let { item ->
                Timber.d("IsFAVVV ${favs}")
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
}


/*
@Preview(showBackground = true)
@Composable
fun MainGridPreview() {
    MovieDbComposeTheme {
        DiscoverMoviesGrid(
            movies = listOf(
                Movie(
                    id = 0,
                    title = "Movie Some 1",
                    posterPath = "",
                    voteAverage = 5.5
                ),
                Movie(
                    id = 1,
                    title = "Some movie 2",
                    posterPath = "",
                    voteAverage = 5.5
                ),
                Movie(
                    id = 2,
                    title = "Some movie 3",
                    posterPath = "",
                    voteAverage = 5.5
                ),
                Movie(
                    id = 3,
                    title = "Some movie 4",
                    posterPath = "",
                    voteAverage = 5.5
                ),
                Movie(
                    id = 4,
                    title = "Some movie 5",
                    posterPath = "",
                    voteAverage = 5.5
                ),
                Movie(
                    id = 5,
                    title = "Some movie 6",
                    posterPath = "",
                    voteAverage = 5.5
                ),
                Movie(
                    id = 6,
                    title = "Some movie 7 long text even longer than this",
                    posterPath = "",
                    voteAverage = 5.5
                )
            ),
            onMovieDetails = {}
        )
    }
}
*/
