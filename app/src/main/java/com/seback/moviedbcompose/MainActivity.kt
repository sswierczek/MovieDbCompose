package com.seback.moviedbcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
                MainAppScaffold()
            }
        }
    }
}

@Composable
fun MainAppScaffold() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text("Movie Guide")
            })
        }
    ) { contentPadding ->
        MainScreen(Modifier.padding(contentPadding))
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val response = mainViewModel.result.collectAsState().value
    if (response is Response.Success) {
        if (response.data.isEmpty()) {
            Text(text = "Loading")
        } else {
            MainGrid(modifier, movies = response.data)
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
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(minSize = 160.dp)
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
            AsyncImage(
                model = movie.imagePath,
//                placeholder = Icons.Default.Movie,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .sizeIn(minHeight = 96.dp)
                    .fillMaxWidth()
            )
            Text(
                text = movie.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                modifier = Modifier
                    .paddingFromBaseline(bottom = 16.dp, top = 16.dp)
                    .padding(start = 8.dp, end = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
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
                    title = "Some movie 7 long text even longer than this",
                    imagePath = "https://fastly.picsum.photos/id/293/300/300.jpg?hmac=gEUIxAYywNOF5HLNxZ6xeU1FGszv7GgR4J9RXhWH5Fc"
                )
            )
        )
    }
}