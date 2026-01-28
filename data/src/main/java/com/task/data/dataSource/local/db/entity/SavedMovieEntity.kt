package com.task.data.dataSource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "saved_movies")
data class SavedMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Float
)