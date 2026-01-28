package com.task.model

data class Movie(
    val backdropPath: String?,
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val genreIds: List<Int>,
    val popularity: Double,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
)