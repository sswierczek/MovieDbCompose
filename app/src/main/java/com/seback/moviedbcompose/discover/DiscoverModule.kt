package com.seback.moviedbcompose.discover

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.discover.data.DiscoverRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DiscoverModule {

    @Binds
    @ViewModelScoped
    abstract fun bindDiscoverRepository(
        discoverRepository: DiscoverRepository
    ): Repository.Discover
}