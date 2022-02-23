package com.seback.moviedbcompose.discover.usecases

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import kotlinx.coroutines.flow.Flow

class GetDiscoverMoviesUseCase(
    private val repository: Repository.Discover
) {

    suspend fun execute(page: Int): Flow<Response<List<Movie>>> {
        return repository.discover(page)
    }
}
