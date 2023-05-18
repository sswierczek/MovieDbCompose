package com.seback.moviedbcompose.core.data.models

import com.seback.moviedbcompose.core.data.api.ApiMovieDetails
import com.seback.moviedbcompose.core.data.api.ApiMovieVideo
import kotlinx.datetime.LocalDate

data class MovieDetails(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val backdropPath: String? = null,
    val posterPath: String = "",
    val vote: Double = 0.0,
    val releaseDate: LocalDate = LocalDate.parse("1999-01-01"),
    val youTubeVideosIds: List<String> = emptyList()
)

fun ApiMovieDetails.map(videos: List<ApiMovieVideo>): MovieDetails =
    MovieDetails(
        id,
        title,
        overview,
        if (backdropPath != null) "https://image.tmdb.org/t/p/w500$backdropPath" else null,
        "https://image.tmdb.org/t/p/w500$posterPath",
        voteAverage,
        LocalDate.parse(releaseDate),
        videos.filter { it.isFromYouTube() && it.isTrailer() }.map { it.videoKey }
    )