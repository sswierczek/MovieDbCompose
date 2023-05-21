package com.seback.moviedbcompose.favs.data

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import kotlinx.coroutines.flow.Flow

class FavMovieUseCase(
    private val repository: Repository.Favourites
) {

    suspend fun isFav(movieId: Int) = repository.isFav(movieId)

    fun all(): Flow<List<Movie>> = repository.all()

    suspend fun favSwitch(movie: Movie) {
        repository.favSwitch(movie)
    }
}