package com.seback.moviedbcompose.discover.data

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.core.data.models.map
import com.seback.moviedbcompose.core.data.models.unknownError
import com.seback.moviedbcompose.core.network.NetworkConfig
import com.seback.moviedbcompose.discover.network.RetrofitDiscoverService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit

class DiscoverRepository(
    retrofit: Retrofit,
    private val networkConfig: NetworkConfig
) : Repository.Discover {

    private val service = retrofit.create(RetrofitDiscoverService::class.java)

    override suspend fun discover(page: Int): Flow<Response<List<Movie>>> = flow {
        Log.d("VMD","discover [${Thread.currentThread().name}]")
        when (val response =
            service.discoverMovies(page = page, apiKey = networkConfig.apiKey)) {
            is NetworkResponse.Success -> {
                emit(Response.Success(response.body.results.map { it.map() }))
            }
            is NetworkResponse.Error -> {
                emit(Response.Error(response.error.message, response.error))
            }
            else -> {
                emit(unknownError())
            }
        }
    }
}