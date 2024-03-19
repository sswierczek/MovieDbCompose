package com.seback.moviedbcompose.favs.data

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.favs.data.room.FavMovie
import com.seback.moviedbcompose.favs.data.room.FavMoviesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FavRepository(private val favMoviesDao: FavMoviesDao) : Repository.Favourites {

    override fun all(): Flow<List<Movie>> =
        favMoviesDao.getAll().map { movies -> movies.map { it.toMovie() } }

    override suspend fun isFav(id: Int): Boolean =
        favMoviesDao.findById(id) != null

    override suspend fun favSwitch(movie: Movie) {
        withContext(Dispatchers.IO) {
            if (favMoviesDao.findById(movie.id) == null) {
                favMoviesDao.insertAll(movie.toFavMovie())
            } else {
                favMoviesDao.delete(movie.toFavMovie())
            }
        }
    }
}

private fun Movie.toFavMovie(): FavMovie = FavMovie(
    id = id,
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate.toString()
)
