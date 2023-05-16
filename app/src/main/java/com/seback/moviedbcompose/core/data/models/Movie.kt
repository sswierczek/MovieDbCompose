package com.seback.moviedbcompose.core.data.models

import com.seback.moviedbcompose.core.data.api.ApiMovie

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String = "",
    val voteAverage: Double
)

fun ApiMovie.map(): Movie =
    Movie(
        id,
        title,
        "https://image.tmdb.org/t/p/w500$posterPath",
        "https://image.tmdb.org/t/p/w500$backdropPath",
        voteAverage
    )