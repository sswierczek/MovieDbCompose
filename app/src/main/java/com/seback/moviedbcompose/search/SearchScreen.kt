package com.seback.moviedbcompose.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seback.moviedbcompose.R
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.ui.common.LoadingContent
import com.seback.moviedbcompose.ui.common.MoviesGrid
import com.seback.moviedbcompose.ui.common.SearchBar
import com.seback.moviedbcompose.ui.common.SortButton
import com.seback.moviedbcompose.ui.common.SortOption

@Composable
fun SearchScreen(
    modifier: Modifier,
    onMovieDetails: (Movie) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val movies by viewModel.result.collectAsState()
    val favs by viewModel.favs.collectAsState()
    val selectedSortOrder = remember { mutableStateOf<SortOption>(SortOption.Rating) }

    Column(modifier = modifier) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = R.string.search_hint,
            onChange = {
                viewModel.searchTextChanged(it)
            }
        )
        SortButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 24.dp),
            text = "Sort by",
            selectedSortOrder = selectedSortOrder.value,
            onSelectedSortOrderChange = { newOrder ->
                selectedSortOrder.value = newOrder
                viewModel.sortOrderChanged(newOrder)
            },
            sortOptions = listOf(
                SortOption.Alphabetical,
                SortOption.Newest,
                SortOption.Rating
            )
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
