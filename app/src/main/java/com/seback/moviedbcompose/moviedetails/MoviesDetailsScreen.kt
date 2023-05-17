package com.seback.moviedbcompose.moviedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.ui.common.Rating
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme
import kotlinx.datetime.LocalDate

@Composable
fun MovieDetailsScreen(modifier: Modifier, onBack: () -> Unit) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val response = viewModel.result.collectAsState().value

    if (response is Response.Success) {
        MovieDetails(
            modifier = modifier,
            movieDetails = response.data
        )
    }
}

@Composable
fun MovieDetails(modifier: Modifier, movieDetails: MovieDetails) {
    Column(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
//                .size(100.dp) // PREVIEW
                .padding(bottom = 16.dp),
            model = movieDetails.backdropPath, contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .paddingFromBaseline(bottom = 16.dp),
            text = movieDetails.title,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Visible,
            maxLines = 1
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "${movieDetails.releaseDate.year}",
                style = MaterialTheme.typography.subtitle1
            )
            Rating(
                modifier = Modifier.padding(start = 16.dp),
                vote = movieDetails.vote
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .paddingFromBaseline(bottom = 16.dp),
            text = movieDetails.overview,
            style = MaterialTheme.typography.body2
        )
    }
}

@Preview
@Composable
fun MovieDetailsPreview() {
    MovieDbComposeTheme {
        Scaffold {
            MovieDetails(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                movieDetails = MovieDetails(
                    title = "Some movie",
                    overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
                    vote = 8.5,
                    releaseDate = LocalDate.parse("2023-02-27")
                )
            )
        }
    }
}