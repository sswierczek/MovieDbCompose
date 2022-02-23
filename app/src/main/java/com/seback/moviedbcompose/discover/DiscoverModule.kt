package com.seback.moviedbcompose.discover

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.network.NetworkConfig
import com.seback.moviedbcompose.discover.data.DiscoverRepository
import com.seback.moviedbcompose.discover.usecases.GetDiscoverMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object DiscoverModule {

    @Provides
    fun provideDiscoverRepository(retrofit: Retrofit, networkConfig: NetworkConfig): Repository.Discover =
        DiscoverRepository(retrofit = retrofit, networkConfig = networkConfig)

    @Provides
    fun provideGetDiscoverMoviesUseCase(repository: Repository.Discover) =
        GetDiscoverMoviesUseCase(repository = repository)
}