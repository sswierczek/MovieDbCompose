package com.seback.moviedbcompose.core.data.models

data class MovieProvider(
    val id: Int,
    val logoPath: String,
    val name: String,
    val type: MovieProviderType
)

enum class MovieProviderType {
    RENT,
    BUY,
    FLATRATE,
}
