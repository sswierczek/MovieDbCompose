package com.seback.moviedbcompose.moviedetails

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.seback.moviedbcompose.core.data.models.Response

@Composable
fun MovieDetailsScreen(modifier: Modifier, onBack: () -> Unit) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val response = viewModel.result.collectAsState().value

    if (response is Response.Success) {
        Surface(modifier = modifier) {
            Text(response.data.title)
        }
    }
}