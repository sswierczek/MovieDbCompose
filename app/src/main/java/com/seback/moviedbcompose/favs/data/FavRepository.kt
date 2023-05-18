package com.seback.moviedbcompose.favs.data

import com.seback.moviedbcompose.core.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavRepository : Repository.Favourites {

    // TODO Save to DB
    object Temp {
        val temporaryFavs = mutableListOf<Int>()
    }

    override suspend fun all(): List<Int> {
        return withContext(Dispatchers.IO) {
            Temp.temporaryFavs
        }
    }

    override suspend fun isFav(id: Int): Boolean = Temp.temporaryFavs.contains(id)

    override suspend fun switch(id: Int) {
        withContext(Dispatchers.IO) {
            tempMakeFav(id)
        }
    }

    private fun tempMakeFav(movieId: Int) {
        if (Temp.temporaryFavs.contains(movieId)) Temp.temporaryFavs.remove(movieId) else Temp.temporaryFavs.add(
            movieId
        )
    }
}