package com.seback.moviedbcompose.core.data.api

data class ApiMovieDetailsCombined(
    val data: ApiMovieDetails,
    val youTubeVideos: List<ApiMovieVideo>
)