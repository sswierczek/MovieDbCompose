package com.seback.moviedbcompose.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.seback.moviedbcompose.favs.data.room.FavMovie
import com.seback.moviedbcompose.favs.data.room.FavMoviesDao

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE `FavMovie` ADD COLUMN `release_date` TEXT DEFAULT '1990-01-01'"
        )
    }
}

@Database(
    entities = [FavMovie::class], version = 2,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favMoviesDao(): FavMoviesDao
}
