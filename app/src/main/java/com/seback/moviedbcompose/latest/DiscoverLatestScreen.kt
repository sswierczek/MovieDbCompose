package com.seback.moviedbcompose.latest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.ui.common.LoadingContentLazy
import com.seback.moviedbcompose.ui.common.MovieCard
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme
import kotlinx.coroutines.flow.flowOf

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

@Preview(showBackground = true)
@Composable
fun MainGridPreview() {
    // Not working in preview for now, until paging-compose 1.0.0-alpha20.
    // See https://issuetracker.google.com/issues/194544557
    val items = flowOf(
        PagingData.from(
            List(30) {
                Movie(
                    id = it,
                    title = "Movie Some $it",
                    posterPath = "",
                    voteAverage = 10.0 / it
                )
            })
    ).collectAsLazyPagingItems()

    MovieDbComposeTheme {
        DiscoverMoviesGrid(
            movies = items,
            onMovieDetails = {},
            onFavClick = {},
            favs = emptyList()
        )
    }
}

