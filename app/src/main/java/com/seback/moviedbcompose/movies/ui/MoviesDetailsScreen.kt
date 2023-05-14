package com.seback.moviedbcompose.movies.ui

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MovieDetailsScreen(modifier: Modifier, movieId: Int, onBack: () -> Unit) {
    Surface(modifier = modifier) {
        Text("Movie id $movieId")
    }
}