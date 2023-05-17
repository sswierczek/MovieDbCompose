package com.seback.moviedbcompose.discover

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.ui.common.LoadingContent
import com.seback.moviedbcompose.ui.common.Rating
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme

@Composable
fun DiscoverLatestScreen(
    modifier: Modifier = Modifier,
    onMovieDetails: (Movie) -> Unit,
    discoverLatestViewModel: DiscoverLatestViewModel = hiltViewModel()
) {
    val response = discoverLatestViewModel.result.collectAsState().value

    LoadingContent(modifier = modifier, response, onRetry = {
        discoverLatestViewModel.retry()
    }) { data ->
        DiscoverMoviesGrid(modifier, data, onMovieDetails)
    }
}

@Composable
fun DiscoverMoviesGrid(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieDetails: (Movie) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(minSize = 150.dp)
    ) {
        items(movies) { item ->
            MovieCard(movie = item, Modifier.clickable {
                onMovieDetails(item)
            })
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp, 8.dp)
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.sizeIn(minWidth = 96.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .sizeIn(minHeight = 96.dp)
                        .fillMaxWidth()
                )
            }
            Rating(
                Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .align(Alignment.Start),
                vote = movie.voteAverage
            )
        }
    }
}

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