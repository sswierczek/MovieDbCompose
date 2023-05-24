package com.seback.moviedbcompose.moviedetails.usecases

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.core.data.models.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: Repository.Details
) {

    fun execute(movieId: Int): Flow<Response<MovieDetails>> {
        return repository.fetch(movieId)
    }
}