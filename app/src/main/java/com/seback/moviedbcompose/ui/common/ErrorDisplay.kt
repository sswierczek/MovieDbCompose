package com.seback.moviedbcompose.ui.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDisplay(message: String?) {
    // TODO implement proper error UI
    Text(text = "Error fetching $message")
}