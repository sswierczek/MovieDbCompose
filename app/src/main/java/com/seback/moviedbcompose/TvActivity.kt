package com.seback.moviedbcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Devices.TV_720p
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.Card
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.home.HomeViewModel
import com.seback.moviedbcompose.ui.common.Rating
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.LocalDate

data class Section(
    val title: String,
    val movieList: List<Movie>
)

@AndroidEntryPoint
class TvActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainTvScreen()
            }
        }
    }

    @Composable
    fun MainTvScreen(
        viewModel: HomeViewModel = hiltViewModel()
    ) {
        val response by viewModel.latestMovies.collectAsState()
        LoadData(response = response)
    }

    // TODO Handle errors
    @Composable
    fun LoadData(
        response: Response<List<Movie>>
    ) {
        if (response is Response.Success) {
            CatalogBrowser(
                sectionList = listOf(
                    Section(title = "Latest", movieList = response.data)
                )
            )
        }
    }

    @Composable
    fun CatalogBrowser(
        sectionList: List<Section>,
        modifier: Modifier = Modifier,
        onItemSelected: (Movie) -> Unit = {},
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sectionList.size) { index ->
                Section(sectionList[index], onItemSelected = onItemSelected)
            }
        }
    }

    @Composable
    fun Section(
        section: Section,
        modifier: Modifier = Modifier,
        onItemSelected: (Movie) -> Unit = {},
    ) {
        Text(
            text = section.title,
            style = MaterialTheme.typography.headlineSmall,
        )
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(section.movieList.size) { index ->
                val movie = section.movieList[index]
                MovieCard(
                    movie = movie,
                    onClick = { onItemSelected(movie) }
                )
            }
        }
    }

    @Composable
    fun MovieCard(
        movie: Movie,
        modifier: Modifier = Modifier,
        onClick: () -> Unit = {}
    ) {
        Card(
            modifier = modifier.size(160.dp, 90.dp),
            onClick = onClick
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = movie.backdropPath,
                    contentDescription = movie.title,
                )
                Rating(
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
                        .background(
                            MaterialTheme.colorScheme.secondary
                        ),
                    movie.voteAverage,
                )
            }
        }
    }

    @Preview(showBackground = true, device = TV_720p)
    @Composable
    fun MainGridPreview() {
        MaterialTheme {
            CatalogBrowser(
                sectionList = listOf(
                    Section(
                        title = "Latest", movieList = movies
                    )
                )
            )
        }
    }

    val movies = listOf(
        Movie(
            id = 0,
            title = "Movie Some 1",
            posterPath = "",
            voteAverage = 5.5,
            releaseDate = LocalDate.parse("2021-01-01")
        ),
        Movie(
            id = 1,
            title = "Some movie 2",
            posterPath = "",
            voteAverage = 5.5,
            releaseDate = LocalDate.parse("2021-01-01")
        ),
        Movie(
            id = 2,
            title = "Some movie 3",
            posterPath = "",
            voteAverage = 5.5,
            releaseDate = LocalDate.parse("2021-01-01")
        ),
        Movie(
            id = 3,
            title = "Some movie 4",
            posterPath = "",
            voteAverage = 5.5,
            releaseDate = LocalDate.parse("2021-01-01")
        ),
        Movie(
            id = 4,
            title = "Some movie 5",
            posterPath = "",
            voteAverage = 5.5,
            releaseDate = LocalDate.parse("2021-01-01")
        ),
        Movie(
            id = 5,
            title = "Some movie 6",
            posterPath = "",
            voteAverage = 5.5,
            releaseDate = LocalDate.parse("2021-01-01")
        ),
        Movie(
            id = 6,
            title = "Some movie 7 long text even longer than this",
            posterPath = "",
            voteAverage = 5.5,
            releaseDate = LocalDate.parse("2021-01-01")
        )
    )
}
