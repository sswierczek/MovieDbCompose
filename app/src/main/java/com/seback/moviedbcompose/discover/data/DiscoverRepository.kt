package com.seback.moviedbcompose.discover.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.core.data.models.map
import com.seback.moviedbcompose.core.data.models.unknownError
import com.seback.moviedbcompose.core.network.NetworkConfig
import com.seback.moviedbcompose.discover.network.RetrofitDiscoverService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class DiscoverRepository(
    retrofit: Retrofit,
    private val networkConfig: NetworkConfig
) : Repository.Discover {

    private val service = retrofit.create(RetrofitDiscoverService::class.java)

    override suspend fun discover(page: Int): Response<List<Movie>> =
        withContext(Dispatchers.IO) {
            when (val response =
                service.discoverMovies(page = page, apiKey = networkConfig.apiKey)) {
                is NetworkResponse.Success -> {
                    Response.Success(response.body.results.map { it.map() })
                }
                is NetworkResponse.Error -> {
                    Response.Error(response.error.message, response.error)
                }
                else -> unknownError()
            }
        }
}