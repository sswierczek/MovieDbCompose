package com.seback.moviedbcompose.discover.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.api.ApiErrorResponse
import com.seback.moviedbcompose.core.data.api.ApiMovieResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitDiscoverService {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): NetworkResponse<ApiMovieResult, ApiErrorResponse>
}