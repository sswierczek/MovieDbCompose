package com.seback.moviedbcompose.search

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.search.data.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(
        searchRepository: SearchRepository
    ): Repository.Search
}