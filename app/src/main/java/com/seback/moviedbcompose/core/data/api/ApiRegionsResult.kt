package com.seback.moviedbcompose.core.data.api

data class ApiRegion(
    val iso_3166_1: String,
    val english_name: String,
    val native_name: String
)

data class ApiRegionsResult(
    val results: List<ApiRegion>
)