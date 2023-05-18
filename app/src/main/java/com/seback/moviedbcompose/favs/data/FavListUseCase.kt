package com.seback.moviedbcompose.favs.data

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import kotlinx.coroutines.flow.Flow

class FavListUseCase(
    private val repositoryDiscover: Repository.Discover,
    private val repositoryFav: Repository.Favourites
) {

    suspend fun execute(): Flow<Response<List<Movie>>> =
        repositoryDiscover.withIds(moviesIds = repositoryFav.all())
}