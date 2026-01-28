package com.task.data.reposiory

import com.task.data.dataSource.local.db.dao.MovieDao
import com.task.data.dataSource.local.db.entity.SavedMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedMovieRepository @Inject constructor(
    private val dao: MovieDao
) {

    fun getSavedMovies(): Flow<List<SavedMovieEntity>> = dao.getAllSavedMovies()

    suspend fun saveMovie(movie: SavedMovieEntity) = dao.insertMovie(movie)

    suspend fun removeMovie(movie: SavedMovieEntity) = dao.deleteMovie(movie)

    fun isSaved(id: Int): Flow<Boolean> = dao.isMovieSaved(id)
}