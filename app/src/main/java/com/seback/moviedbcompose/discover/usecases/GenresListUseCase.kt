package com.seback.moviedbcompose.discover.usecases

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Genre
import com.seback.moviedbcompose.core.data.models.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenresListUseCase @Inject constructor(
    private val repository: Repository.Discover
) {

    fun execute(): Flow<List<Genre>> {
        return repository.genres().map {
            when (it) {
                is Response.Success -> {
                    it.data
                }

                else -> {
                    emptyList()
                }
            }
        }
    }
}