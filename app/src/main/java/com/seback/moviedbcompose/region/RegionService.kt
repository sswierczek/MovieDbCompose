package com.seback.moviedbcompose.region

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.api.ApiErrorResponse
import com.seback.moviedbcompose.core.data.api.ApiRegionsResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitRegionService {

    @GET("watch/providers/regions")
    suspend fun regions(
        @Query("api_key") apiKey: String
    ): NetworkResponse<ApiRegionsResult, ApiErrorResponse>
}