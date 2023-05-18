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
        suspend fun latest(page: Int): Flow<Response<List<Movie>>>

        suspend fun discover(page: Int): Flow<Response<List<Movie>>>
    }

    interface Details {
        suspend fun fetch(id: Int): Flow<Response<MovieDetails>>
    }

    interface Favourites {
        suspend fun all(): List<Int>

        suspend fun isFav(id: Int): Boolean

        suspend fun switch(id: Int)
    }
}