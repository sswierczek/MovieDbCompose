package com.seback.moviedbcompose.core.data.models

import com.seback.moviedbcompose.core.data.api.ApiMovieDetails
import com.seback.moviedbcompose.core.data.api.ApiMovieProvider
import com.seback.moviedbcompose.core.data.api.ApiMovieVideo
import com.seback.moviedbcompose.core.data.api.ApiRegion
import kotlinx.datetime.LocalDate

data class MovieDetails(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val backdropPath: String? = null,
    val posterPath: String = "",
    val vote: Double = 0.0,
    val releaseDate: LocalDate = LocalDate.parse("1999-01-01"),
    val youTubeVideosIds: List<String> = emptyList(),
    val providers: List<MovieProvider> = emptyList(),
    val moreInfoUrl: String = "",
    val regions: List<MovieRegion> = emptyList()
) {
    fun toMovie() = Movie(
        id,
        title,
        posterPath,
        backdropPath,
        vote,
        releaseDate
    )
}

fun ApiMovieDetails.map(
    videos: List<ApiMovieVideo>,
    flatRateProviders: List<ApiMovieProvider>,
    rentProviders: List<ApiMovieProvider>,
    buyProviders: List<ApiMovieProvider>,
    link: String,
    regionsResult: List<ApiRegion>
): MovieDetails =
    MovieDetails(
        id,
        title,
        overview,
        if (backdropPath != null) "https://image.tmdb.org/t/p/w500$backdropPath" else null,
        "https://image.tmdb.org/t/p/w500$posterPath",
        voteAverage,
        LocalDate.parse(releaseDate),
        videos.filter { it.isFromYouTube() && it.isTrailer() && it.official }.map { it.videoKey },
        flatRateProviders.map { it.map(MovieProviderType.FLATRATE) } +
                rentProviders.map { it.map(MovieProviderType.RENT) } +
                buyProviders.map { it.map(MovieProviderType.BUY) },
        moreInfoUrl = link,
        regionsResult.map { it.map() }
    )

fun ApiMovieProvider.map(type: MovieProviderType): MovieProvider = MovieProvider(
    id = providerId,
    logoPath = "https://image.tmdb.org/t/p/original$logoPath",
    name = providerName,
    type = type
)