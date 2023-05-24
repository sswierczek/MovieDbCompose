package com.seback.moviedbcompose.discover

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.seback.moviedbcompose.R
import com.seback.moviedbcompose.ui.common.SearchBar
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme

@Composable
fun DiscoverMoviesScreen(
    modifier: Modifier,
    viewModel: DiscoverMoviesViewModel = hiltViewModel()
) {

    SearchBar(
        modifier = modifier.fillMaxWidth(),
        placeHolder = R.string.search,
        onChange = {
            viewModel.searchTextChanged(it)
        }
    )
}

@Preview
@Composable
fun DiscoverScreenPreview() {
    MovieDbComposeTheme {
        DiscoverMoviesScreen(modifier = Modifier)
    }
}
