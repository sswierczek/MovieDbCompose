package com.seback.moviedbcompose.core.data

import android.content.Context
import androidx.room.Room
import com.seback.moviedbcompose.core.data.room.AppDatabase
import com.seback.moviedbcompose.core.data.room.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "moviedb-database"
        ).addMigrations(MIGRATION_1_2).build()
    }
}
