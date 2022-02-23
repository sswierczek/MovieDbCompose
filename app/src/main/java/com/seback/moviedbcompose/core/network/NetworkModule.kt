package com.seback.moviedbcompose.core.network

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.seback.moviedbcompose.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkConfig() = NetworkConfig()

    @Singleton
    @Provides
    fun provideOkClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val levelBody = HttpLoggingInterceptor.Level.BODY
        val levelNone = HttpLoggingInterceptor.Level.NONE
        logging.level = if (BuildConfig.DEBUG) levelBody else levelNone
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okClient: OkHttpClient, networkConfig: NetworkConfig): Retrofit =
        Retrofit.Builder()
            .baseUrl(networkConfig.baseUrl)
            .client(okClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
}