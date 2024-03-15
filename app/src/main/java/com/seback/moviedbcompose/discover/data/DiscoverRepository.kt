package com.seback.moviedbcompose.discover.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.core.data.models.map
import com.seback.moviedbcompose.core.data.models.unknownError
import com.seback.moviedbcompose.core.network.NetworkConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import timber.log.Timber

class DiscoverRepository(
    retrofit: Retrofit,
    private val networkConfig: NetworkConfig
) : Repository.Discover {

    private val service = retrofit.create(RetrofitDiscoverService::class.java)
    override fun search(query: String, page: Int): Flow<Response<List<Movie>>> = flow {
        when (val response =
            service.search(query = query, page = page, apiKey = networkConfig.apiKey)) {
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

    override fun discover(page: Int): Flow<Response<List<Movie>>> = flow {
        Timber.d("discover [${Thread.currentThread().name}]")
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

    override fun genres(): Flow<Response<List<String>>> = flow {
        Timber.d("genres [${Thread.currentThread().name}]")
        when (val response =
            service.genres(apiKey = networkConfig.apiKey)) {
            is NetworkResponse.Success -> {
                emit(Response.Success((response.body.genres ?: emptyList()).map { it.name }))
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