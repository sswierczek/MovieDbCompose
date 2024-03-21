package com.seback.moviedbcompose.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seback.moviedbcompose.core.data.models.Genre
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.discover.data.DiscoverOptions
import com.seback.moviedbcompose.ui.common.LoadingContent
import com.seback.moviedbcompose.ui.common.MoviesGrid
import com.seback.moviedbcompose.ui.common.SortButton
import com.seback.moviedbcompose.ui.common.SortOption

@Composable
fun DiscoverMoviesScreen(
    modifier: Modifier,
    onMovieDetails: (Movie) -> Unit,
    viewModel: DiscoverMoviesViewModel = hiltViewModel()
) {

    val movies by viewModel.result.collectAsState()
    val genres by viewModel.genres.collectAsState()
    val favs by viewModel.favs.collectAsState()
    val selectedSortOrder = rememberSaveable { mutableStateOf<SortOption>(SortOption.Rating) }
    val selectedGenres = rememberSaveable { mutableStateOf(emptyList<Genre>()) }
    val selectedStartYear = rememberSaveable { mutableIntStateOf(2022) }
    val selectedEndYear = rememberSaveable { mutableIntStateOf(2022) }

    fun discoverOptions() = DiscoverOptions(
        selectedGenres = selectedGenres.value,
        selectedStartYear = selectedStartYear.intValue,
        selectedEndYear = selectedEndYear.intValue
    )

    Column(modifier = modifier) {
        SortButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 24.dp),
            text = "Sort by",
            selectedSortOrder = selectedSortOrder.value,
            onSelectedSortOrderChange = { sortOrder ->
                selectedSortOrder.value = sortOrder
                viewModel.sortOrderChanged(discoverOptions(), sortOrder)
            },
            sortOptions = listOf(
                SortOption.Alphabetical,
                SortOption.Popularity,
                SortOption.Newest,
                SortOption.Rating
            )
        )
        DiscoverFilterScreen(
            modifier = Modifier,
            genres = genres,
            selectedGenres = selectedGenres.value,
            selectedStartYear = selectedStartYear.intValue,
            selectedEndYear = selectedEndYear.intValue,
            onSelectedGenresChange = { selectedGenres.value = it },
            onSelectedStartChange = { selectedStartYear.intValue = it },
            onSelectedEndYearChange = { selectedEndYear.intValue = it },
            onDiscoverClick = {
                viewModel.discoverBy(discoverOptions())
            }
        )
        LoadingContent(modifier = Modifier,
            response = movies,
            onRetry = viewModel::retry,
            isEmptyCheck = { it.isEmpty() }
        ) {
            MoviesGrid(
                movies = it,
                onMovieDetails = onMovieDetails,
                favs = favs,
                onFavClick = viewModel::favSwitch
            )
        }
    }
}
