package com.seback.moviedbcompose.moviedetails

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.ui.common.LoadingContent
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme
import kotlinx.datetime.LocalDate

@Composable
fun MovieDetailsScreen(
    modifier: Modifier,
    onBack: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val response by viewModel.result.collectAsState()
    val isFav by viewModel.isFav.collectAsState()
    LoadingContent(modifier = modifier,
        response = response,
        isEmptyCheck = { false },
        onRetry = { viewModel.retry() }
    ) {
        MovieDetails(
            modifier = modifier,
            movieDetails = it,
            isFav = isFav,
            onFavClick = {
                viewModel.onFavClicked()
            },
            onRegionSelectionChange = { region ->
                viewModel.onRegionSelectionChange(region)
            }
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
                ),
                isFav = true,
                onFavClick = {},
                onRegionSelectionChange = {}
            )
        }
    }
}