package com.seback.moviedbcompose.search.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.api.ApiErrorResponse
import com.seback.moviedbcompose.core.data.api.ApiMovieResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitSearchService {

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): NetworkResponse<ApiMovieResult, ApiErrorResponse>
}