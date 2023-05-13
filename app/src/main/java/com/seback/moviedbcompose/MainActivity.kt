package com.seback.moviedbcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieDbComposeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val response = mainViewModel.result.collectAsState().value
    if (response is Response.Success) {
        if (response.data.isEmpty()) {
            Text(text = "Loading")
        } else {
            MainGrid(movies = response.data)
        }
    } else if (response is Response.Error) {
        Text(text = "Error fetching ${response.message}")
    }
}

@Composable
fun MainGrid(
    modifier: Modifier = Modifier,
    movies: List<Movie>
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 96.dp)
    ) {
        items(movies) { item ->
            MovieCard(movie = item)
        }
    }

}

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier.width(192.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = movie.imagePath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(96.dp)
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MainGridPreview() {
    MovieDbComposeTheme {
        MainGrid(
            movies = listOf(
                Movie(
                    id = 0,
                    title = "Movie Some 1",
                    imagePath = "https://fastly.picsum.photos/id/293/300/300.jpg?hmac=gEUIxAYywNOF5HLNxZ6xeU1FGszv7GgR4J9RXhWH5Fc"
                ),
                Movie(
                    id = 1,
                    title = "Some movie 2",
                    imagePath = "https://fastly.picsum.photos/id/293/300/300.jpg?hmac=gEUIxAYywNOF5HLNxZ6xeU1FGszv7GgR4J9RXhWH5Fc"
                ),
                Movie(
                    id = 2,
                    title = "Some movie 3",
                    imagePath = "https://fastly.picsum.photos/id/293/300/300.jpg?hmac=gEUIxAYywNOF5HLNxZ6xeU1FGszv7GgR4J9RXhWH5Fc"
                ),
                Movie(
                    id = 3,
                    title = "Some movie 4",
                    imagePath = "https://fastly.picsum.photos/id/293/300/300.jpg?hmac=gEUIxAYywNOF5HLNxZ6xeU1FGszv7GgR4J9RXhWH5Fc"
                ),
                Movie(
                    id = 4,
                    title = "Some movie 5",
                    imagePath = "https://fastly.picsum.photos/id/293/300/300.jpg?hmac=gEUIxAYywNOF5HLNxZ6xeU1FGszv7GgR4J9RXhWH5Fc"
                ),
                Movie(
                    id = 5,
                    title = "Some movie 6",
                    imagePath = "https://fastly.picsum.photos/id/293/300/300.jpg?hmac=gEUIxAYywNOF5HLNxZ6xeU1FGszv7GgR4J9RXhWH5Fc"
                ),
                Movie(
                    id = 6,
                    title = "Some movie 7",
                    imagePath = "https://fastly.picsum.photos/id/293/300/300.jpg?hmac=gEUIxAYywNOF5HLNxZ6xeU1FGszv7GgR4J9RXhWH5Fc"
                )
            )
        )
    }
}