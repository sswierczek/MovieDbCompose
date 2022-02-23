package com.seback.moviedbcompose.core.data

import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.core.data.models.Response

/**
 * Groups data repositories.
 *
 * Categorized by API categories https://developers.themoviedb.org/3/
 */
interface Repository {
    interface Discover {
        suspend fun discover(page: Int): Response<List<Movie>>
    }

    interface Details {
        suspend fun fetch(id: Int): Response<MovieDetails>
    }
}