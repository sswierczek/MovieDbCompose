package com.seback.moviedbcompose.core.data

import com.seback.moviedbcompose.core.data.models.Genre
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.core.data.models.MovieRegion
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.discover.data.DiscoverOptions
import com.seback.moviedbcompose.ui.data.SortOption
import kotlinx.coroutines.flow.Flow

/**
 * Groups data repositories.
 *
 * Categorized by API categories https://developers.themoviedb.org/3/
 */
interface Repository {

    interface Home {
        enum class HomeDataType {
            LATEST,
            UPCOMING,
            POPULAR,
            TOP
        }

        fun fetch(type: HomeDataType, page: Int): Flow<Response<List<Movie>>>
    }

    interface Search {
        fun search(query: String, page: Int): Flow<Response<List<Movie>>>
    }

    interface Discover {
        fun genres(): Flow<Response<List<Genre>>>
        fun discover(
            page: Int,
            options: DiscoverOptions?,
            sortOption: SortOption
        ): Flow<Response<List<Movie>>>
    }

    interface Details {
        fun fetch(id: Int, region: MovieRegion): Flow<Response<MovieDetails>>
    }

    interface Favourites {
        fun all(): Flow<List<Movie>>

        suspend fun isFav(id: Int): Boolean

        suspend fun favSwitch(movie: Movie)
    }
}