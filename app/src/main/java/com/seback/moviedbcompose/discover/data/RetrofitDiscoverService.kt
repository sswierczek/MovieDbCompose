package com.seback.moviedbcompose.discover.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.api.ApiErrorResponse
import com.seback.moviedbcompose.core.data.api.ApiMovieGenres
import com.seback.moviedbcompose.core.data.api.ApiMovieResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitDiscoverService {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") genres: String? = null,
        @Query("primary_release_date.gte") yearStart: String? = null,
        @Query("primary_release_date.lte") yearEnd: String? = null
    ): NetworkResponse<ApiMovieResult, ApiErrorResponse>

    @GET("genre/movie/list")
    suspend fun genres(
        @Query("api_key") apiKey: String,
    ): NetworkResponse<ApiMovieGenres, ApiErrorResponse>
}