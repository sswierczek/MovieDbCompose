package com.seback.moviedbcompose.core.data.models

import com.seback.moviedbcompose.core.data.api.ApiMovie
import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String? = "",
    val backdropPath: String? = null,
    val voteAverage: Double,
    val releaseDate: LocalDate
)

fun emptyMovie() = Movie(
    id = -1,
    title = "",
    voteAverage = 0.0,
    releaseDate = LocalDate.parse("1999-01-01")
)

fun ApiMovie.map(): Movie {
    val date = try {
        LocalDate.parse(releaseDate)
    } catch (e: Exception) {
        LocalDate.parse("1999-01-01")
    }
    return Movie(
        id,
        title,
        "https://image.tmdb.org/t/p/w500$posterPath",
        "https://image.tmdb.org/t/p/w500$backdropPath",
        voteAverage,
        date
    )
}