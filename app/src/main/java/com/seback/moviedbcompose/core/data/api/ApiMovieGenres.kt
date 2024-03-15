package com.seback.moviedbcompose.core.data.api

import com.google.gson.annotations.SerializedName

data class ApiMovieGenres(
    @SerializedName("genres")
    val genres: List<Genre>?
)

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)