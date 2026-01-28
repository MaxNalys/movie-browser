package com.task.data.dataSource.remote.tmdbApi.model

import kotlinx.serialization.Serializable


@Serializable
data class GenreResponse(
    val genres: List<GenreDto>
)