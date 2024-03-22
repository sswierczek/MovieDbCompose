package com.seback.moviedbcompose.moviedetails.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.api.ApiErrorResponse
import com.seback.moviedbcompose.core.data.api.ApiMovieDetails
import com.seback.moviedbcompose.core.data.api.ApiMovieProviders
import com.seback.moviedbcompose.core.data.api.ApiMovieVideosResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitMovieDetailsService {

    @GET("movie/{movieId}")
    suspend fun movieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): NetworkResponse<ApiMovieDetails, ApiErrorResponse>

    @GET("movie/{movieId}/videos")
    suspend fun movieVideos(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): NetworkResponse<ApiMovieVideosResult, ApiErrorResponse>

    @GET("movie/{movieId}/watch/providers")
    suspend fun movieProviders(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): NetworkResponse<ApiMovieProviders, ApiErrorResponse>
}