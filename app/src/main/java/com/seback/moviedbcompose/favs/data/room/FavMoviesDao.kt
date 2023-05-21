package com.seback.moviedbcompose.favs.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavMoviesDao {

    @Query("SELECT * FROM FavMovie")
    fun getAll(): Flow<List<FavMovie>>

    @Query("SELECT * FROM FavMovie WHERE id LIKE :id LIMIT 1")
    suspend fun findById(id: Int): FavMovie?

    @Insert
    suspend fun insertAll(vararg movies: FavMovie)

    @Delete
    suspend fun delete(movie: FavMovie)
}