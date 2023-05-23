package com.seback.moviedbcompose.home.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.api.ApiErrorResponse
import com.seback.moviedbcompose.core.data.api.ApiMovieResult
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeRetrofitService {

    @GET("movie/now_playing")
    suspend fun latest(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): NetworkResponse<ApiMovieResult, ApiErrorResponse>

    @GET("movie/popular")
    suspend fun popular(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): NetworkResponse<ApiMovieResult, ApiErrorResponse>

    @GET("movie/top_rated")
    suspend fun top(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): NetworkResponse<ApiMovieResult, ApiErrorResponse>
}