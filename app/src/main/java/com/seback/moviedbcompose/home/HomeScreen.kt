package com.seback.moviedbcompose.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.ui.common.LoadingContentLazy
import com.seback.moviedbcompose.ui.common.MovieCard
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieDetails: (Movie) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var dataType by rememberSaveable {
        mutableStateOf(Repository.Home.HomeDataType.LATEST)
    }

    val moviesLazy = when (dataType) {
        Repository.Home.HomeDataType.LATEST -> homeViewModel.latest
        Repository.Home.HomeDataType.POPULAR -> homeViewModel.popular
        Repository.Home.HomeDataType.UPCOMING -> homeViewModel.upcoming
        Repository.Home.HomeDataType.TOP -> homeViewModel.top
    }.collectAsLazyPagingItems()

    val favs by homeViewModel.favs.collectAsState()

    Column(modifier = modifier) {
        HomeTabs(onTab = {
            dataType = it
        })
        HomeContent(modifier = Modifier,
            movies = moviesLazy,
            onMovieDetails = onMovieDetails,
            favs = favs,
            onFavClick = {
                homeViewModel.favSwitch(it)
            })
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    onMovieDetails: (Movie) -> Unit,
    favs: List<Movie>,
    onFavClick: (Movie) -> Unit
) {
    LoadingContentLazy(modifier = modifier, response = movies, onRetry = {
        movies.retry()
    })
    HomeGrid(
        modifier = modifier,
        movies = movies,
        onMovieDetails = onMovieDetails,
        favs = favs,
        onFavClick = onFavClick
    )
}

@Composable
fun HomeGrid(
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

@Preview
@Composable
fun HomeGridPreview() {
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
        HomeGrid(
            movies = items,
            onMovieDetails = {},
            onFavClick = {},
            favs = emptyList()
        )
    }
}