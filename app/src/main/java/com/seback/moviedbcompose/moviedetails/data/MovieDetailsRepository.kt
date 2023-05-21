package com.seback.moviedbcompose.moviedetails.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.api.ApiMovieDetails
import com.seback.moviedbcompose.core.data.api.ApiMovieVideo
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.core.data.models.map
import com.seback.moviedbcompose.core.data.models.unknownError
import com.seback.moviedbcompose.core.network.NetworkConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import timber.log.Timber

class MovieDetailsRepository(
    retrofit: Retrofit,
    private val networkConfig: NetworkConfig
) : Repository.Details {

    private val service = retrofit.create(RetrofitMovieDetailsService::class.java)

    override fun fetch(id: Int): Flow<Response<MovieDetails>> = flow {
        Timber.d("fetch movie details id $id [${Thread.currentThread().name}]")
        when (val response =
            service.movieDetails(apiKey = networkConfig.apiKey, movieId = id)) {
            is NetworkResponse.Success -> {
                val movie = response.body
                val videos = videosForMovie(movie)
                emit(Response.Success(movie.map(videos)))
            }

            is NetworkResponse.Error -> {
                emit(Response.Error(response.error.message, response.error))
            }

            else -> {
                emit(unknownError())
            }
        }
    }

    private suspend fun videosForMovie(movie: ApiMovieDetails): List<ApiMovieVideo> {
        val videosResponse = service.movieVideos(apiKey = networkConfig.apiKey, movieId = movie.id)
        return if (videosResponse is NetworkResponse.Success) {
            videosResponse.body.results
        } else {
            emptyList()
        }

    }
}