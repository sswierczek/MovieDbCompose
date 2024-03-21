package com.seback.moviedbcompose.core.data.models

import com.seback.moviedbcompose.core.data.api.ApiMovie
import com.seback.moviedbcompose.ui.data.SortOption
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

fun Response.Success<List<Movie>>.sortDataBy(sortOption: SortOption): Response.Success<List<Movie>> =
    Response.Success(when (sortOption) {
        SortOption.Alphabetical -> data.sortedBy { it.title }
        SortOption.Newest -> data.sortedByDescending { it.releaseDate }
        SortOption.Rating -> data.sortedByDescending { it.voteAverage }
        else -> data // TODO map popularity from API to be able to sort
    })
