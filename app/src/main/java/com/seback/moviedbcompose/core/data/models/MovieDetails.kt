package com.seback.moviedbcompose.core.data.models

import com.seback.moviedbcompose.core.data.api.ApiMovieDetails
import com.seback.moviedbcompose.core.data.api.ApiMovieVideo

data class MovieDetails(
    val id: Int,
    val title: String,
    val imagePath: String,
    val vote: String,
    val youTubeVideosIds: List<String> = emptyList()
)

fun ApiMovieDetails.map(videos: List<ApiMovieVideo>): MovieDetails =
    MovieDetails(
        id,
        title,
        "https://image.tmdb.org/t/p/w500$posterPath",
        "$voteAverage",
        videos.filter { it.isFromYouTube() && it.isTrailer() }.map { it.videoKey }
    )