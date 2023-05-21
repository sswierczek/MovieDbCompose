package com.seback.moviedbcompose.core.data

import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.core.data.models.Response
import kotlinx.coroutines.flow.Flow

/**
 * Groups data repositories.
 *
 * Categorized by API categories https://developers.themoviedb.org/3/
 */
interface Repository {
    interface Discover {
        fun withIds(moviesIds: List<Int>): Flow<Response<List<Movie>>>

        fun latest(page: Int): Flow<Response<List<Movie>>>

        fun discover(page: Int): Flow<Response<List<Movie>>>
    }

    interface Details {
        fun fetch(id: Int): Flow<Response<MovieDetails>>
    }

    interface Favourites {
        fun all(): Flow<List<Movie>>

        suspend fun isFav(id: Int): Boolean

        suspend fun favSwitch(movie: Movie)
    }
}