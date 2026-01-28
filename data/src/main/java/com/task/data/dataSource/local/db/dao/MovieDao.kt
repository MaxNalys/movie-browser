package com.task.data.dataSource.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.data.dataSource.local.db.entity.SavedMovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Query("SELECT * FROM saved_movies")
    fun getAllSavedMovies(): Flow<List<SavedMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: SavedMovieEntity)

    @Delete
    suspend fun deleteMovie(movie: SavedMovieEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_movies WHERE id = :movieId)")
    fun isMovieSaved(movieId: Int): Flow<Boolean>
}