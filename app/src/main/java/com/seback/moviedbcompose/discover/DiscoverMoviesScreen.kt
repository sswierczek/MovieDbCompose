package com.seback.moviedbcompose.discover

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.seback.moviedbcompose.ui.common.MovieCard
import com.seback.moviedbcompose.ui.common.SearchBar
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
    val selectedSortOrder = remember { mutableStateOf<SortOption>(SortOption.Rating) }

    Column(modifier = modifier) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = R.string.search,
            onChange = {
                viewModel.searchTextChanged(it)
            }
        )
        SortButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
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
        DiscoverFilterScreen(
            modifier = Modifier,
            genres = genres
        )
        LoadingContent(modifier = Modifier,
            response = movies,
            onRetry = viewModel::retry,
            isEmptyCheck = { it.isEmpty() }
        ) {
            DiscoverGrid(
                movies = it,
                onMovieDetails = onMovieDetails,
                favs = favs,
                onFavClick = viewModel::favSwitch
            )
        }

    }
}

@Composable
fun DiscoverGrid(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieDetails: (Movie) -> Unit,
    favs: List<Movie>,
    onFavClick: (Movie) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(minSize = 150.dp)
    ) {
        items(movies)
        { item ->
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
