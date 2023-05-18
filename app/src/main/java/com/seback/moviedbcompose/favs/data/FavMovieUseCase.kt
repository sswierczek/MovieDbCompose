package com.seback.moviedbcompose.favs.data

import com.seback.moviedbcompose.core.data.Repository

class FavMovieUseCase(
    private val repository: Repository.Favourites
) {

    suspend fun isFav(movieId: Int) = repository.isFav(movieId)

    suspend fun all(): List<Int> = repository.all()

    suspend fun switch(id: Int) {
        repository.switch(id)
    }
}