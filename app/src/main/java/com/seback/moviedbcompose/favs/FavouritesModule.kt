package com.seback.moviedbcompose.favs

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.room.AppDatabase
import com.seback.moviedbcompose.favs.data.FavMovieUseCase
import com.seback.moviedbcompose.favs.data.FavRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FavouritesModule {

    @Provides
    @ViewModelScoped
    fun provideFavRepository(appDatabase: AppDatabase): Repository.Favourites =
        FavRepository(appDatabase.favMoviesDao())

    @Provides
    @ViewModelScoped
    fun provideFavMovieUseCase(repository: Repository.Favourites) =
        FavMovieUseCase(repository)
}