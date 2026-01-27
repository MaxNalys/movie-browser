package com.task.data.dataSource.remote.tmdbApi.mapper

import com.task.data.dataSource.remote.tmdbApi.model.MovieDto
import com.task.model.Movie


fun MovieDto.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath.toPosterUrl(TmdbImageConfig.POSTER_SIZE),
        backdropPath = backdropPath.toPosterUrl(TmdbImageConfig.BACKDROP_SIZE),
        popularity = popularity,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount
    )

object TmdbImageConfig {
    const val BASE_URL = "https://image.tmdb.org/t/p/"
    const val POSTER_SIZE = "w300"
    const val BACKDROP_SIZE = "w780"
}

private fun String?.toPosterUrl(size: String): String =
    if (this.isNullOrBlank()) "" else
        "${TmdbImageConfig.BASE_URL}$size$this"