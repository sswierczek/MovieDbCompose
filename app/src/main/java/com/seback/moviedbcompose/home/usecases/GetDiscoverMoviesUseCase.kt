package com.seback.moviedbcompose.home.usecases

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeLatest @Inject constructor(
    private val repository: Repository.Home
) {

    fun execute(page: Int): Flow<Response<List<Movie>>> {
        return repository.fetch(Repository.Home.HomeDataType.LATEST, page)
    }
}