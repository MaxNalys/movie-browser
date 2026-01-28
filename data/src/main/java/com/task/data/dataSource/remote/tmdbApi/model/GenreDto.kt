package com.task.data.dataSource.remote.tmdbApi.model

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)