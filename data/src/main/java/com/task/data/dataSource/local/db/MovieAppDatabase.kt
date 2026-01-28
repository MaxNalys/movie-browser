package com.task.data.dataSource.local.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.data.dataSource.local.db.dao.MovieDao
import com.task.data.dataSource.local.db.entity.SavedMovieEntity

@Database(
    entities = [SavedMovieEntity::class],
    version = 1
)
abstract class MovieAppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}