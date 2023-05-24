package com.seback.moviedbcompose.home.data

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

class HomeRepository(
    retrofit: Retrofit,
    private val networkConfig: NetworkConfig
) : Repository.Home {

    private val service = retrofit.create(HomeRetrofitService::class.java)

    override fun fetch(type: Repository.Home.HomeDataType, page: Int): Flow<Response<List<Movie>>> = flow {
        Timber.d("fetch $type [${Thread.currentThread().name}]")
        val response = when (type) {
            Repository.Home.HomeDataType.LATEST -> service.latest(page = page, apiKey = networkConfig.apiKey)
            Repository.Home.HomeDataType.POPULAR -> service.popular(page = page, apiKey = networkConfig.apiKey)
            Repository.Home.HomeDataType.TOP -> service.top(page = page, apiKey = networkConfig.apiKey)
            Repository.Home.HomeDataType.UPCOMING -> service.upcoming(page = page, apiKey = networkConfig.apiKey)
        }
        when (response) {
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