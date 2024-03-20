package com.seback.moviedbcompose.search.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.core.data.models.map
import com.seback.moviedbcompose.core.data.models.unknownError
import com.seback.moviedbcompose.core.network.NetworkConfig
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

@ViewModelScoped
class SearchRepository @Inject constructor(
    retrofit: Retrofit,
    private val networkConfig: NetworkConfig
) : Repository.Search {

    private val service = retrofit.create(RetrofitSearchService::class.java)

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
}