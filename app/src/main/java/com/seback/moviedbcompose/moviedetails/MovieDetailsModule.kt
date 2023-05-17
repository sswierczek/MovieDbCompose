package com.seback.moviedbcompose.moviedetails

import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.network.NetworkConfig
import com.seback.moviedbcompose.moviedetails.data.MovieDetailsRepository
import com.seback.moviedbcompose.moviedetails.usecases.GetMovieDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object MovieDetailsModule {

    @Provides
    @ViewModelScoped
    fun provideDiscoverRepository(
        retrofit: Retrofit,
        networkConfig: NetworkConfig
    ): Repository.Details =
        MovieDetailsRepository(retrofit = retrofit, networkConfig = networkConfig)

    @Provides
    @ViewModelScoped
    fun provideGetDiscoverMoviesUseCase(repository: Repository.Details) =
        GetMovieDetailsUseCase(repository = repository)
}