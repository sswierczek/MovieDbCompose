package com.seback.moviedbcompose.discover.usecases

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.discover.data.DiscoverOptions
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverNewMoviesUseCase @Inject constructor(
    private val repository: Repository.Discover
) {

    fun execute(
        options: DiscoverOptions?
    ): Flow<Response<List<Movie>>> {
        return repository.discover(page = 1, options) // TODO Add paging
    }
}