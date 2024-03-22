package com.seback.moviedbcompose.core.data.api

import com.google.gson.annotations.SerializedName

data class ApiMovieProviders(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val resultsInCountry: Map<String, ApiMovieProviderData>
)

data class ApiMovieProviderResultsInCountry(
    @SerializedName("PL") val resultsInPoland: ApiMovieProviderData,
    @SerializedName("US") val resultsInUsa: ApiMovieProviderData
)

data class ApiMovieProviderData(
    @SerializedName("link") val link: String,
    @SerializedName("flatrate") val flatrate: List<ApiMovieProvider>,
    @SerializedName("rent") val rent: List<ApiMovieProvider>,
    @SerializedName("buy") val buy: List<ApiMovieProvider>
)

data class ApiMovieProvider(
    @SerializedName("display_priority") val displayPriority: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_id") val providerId: Int,
    @SerializedName("provider_name") val providerName: String
)