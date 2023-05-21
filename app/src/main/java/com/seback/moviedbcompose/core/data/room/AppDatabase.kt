package com.seback.moviedbcompose.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seback.moviedbcompose.favs.data.room.FavMovie
import com.seback.moviedbcompose.favs.data.room.FavMoviesDao

@Database(entities = [FavMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favMoviesDao(): FavMoviesDao
}