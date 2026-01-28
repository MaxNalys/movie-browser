package com.task.data.dataSource.remote.tmdbApi.mapper

import com.task.data.dataSource.remote.tmdbApi.model.GenreDto
import com.task.model.Genre

fun GenreDto.toDomain(): Genre =
    Genre(
        id = id,
        name = name
    )