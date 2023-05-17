package com.seback.moviedbcompose.moviedetails.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.api.ApiErrorResponse
import com.seback.moviedbcompose.core.data.api.ApiMovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitMovieDetailsService {

    @GET("movie/{movieId}")
    suspend fun movieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): NetworkResponse<ApiMovieDetails, ApiErrorResponse>
}