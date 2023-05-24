package com.seback.moviedbcompose.home

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.network.NetworkConfig
import com.seback.moviedbcompose.home.data.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class HomeModule {

    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        retrofit: Retrofit,
        networkConfig: NetworkConfig,
    ): Repository.Home =
        HomeRepository(
            retrofit = retrofit,
            networkConfig = networkConfig
        )
}
