package com.seback.moviedbcompose.discover.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.api.ApiMovieDetails
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

    override suspend fun withIds(moviesIds: List<Int>): Flow<Response<List<Movie>>> = flow {
        Timber.d("withIds [${Thread.currentThread().name}]")

        val movies = mutableListOf<ApiMovieDetails>()
        for (id in moviesIds) {
            val response = service.movieDetails(apiKey = networkConfig.apiKey, movieId = id)
            if (response is NetworkResponse.Success) {
                movies.add(response.body)
            }
        }
        emit(Response.Success(movies.map { it.map(emptyList()).toMovie() }))
    }

    override suspend fun latest(page: Int): Flow<Response<List<Movie>>> = flow {
        Timber.d("latest [${Thread.currentThread().name}]")
        when (val response =
            service.latest(page = page, apiKey = networkConfig.apiKey)) {
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

    override suspend fun discover(page: Int): Flow<Response<List<Movie>>> = flow {
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
}