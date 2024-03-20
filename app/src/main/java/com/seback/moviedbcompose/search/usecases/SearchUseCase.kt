package com.seback.moviedbcompose.search.usecases

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject


class SearchUseCase @Inject constructor(
    private val repository: Repository.Search
) {

    fun execute(query: String): Flow<Response<List<Movie>>> {
        if (query.isEmpty()) return emptyFlow()

        return repository.search(query, page = 1) // TODO Add paging
    }
}