package com.seback.moviedbcompose.discover.data

import com.seback.moviedbcompose.core.data.models.Genre

data class DiscoverOptions(
    val selectedGenres: List<Genre>,
    val selectedStartYear: Int,
    val selectedEndYear: Int
)
