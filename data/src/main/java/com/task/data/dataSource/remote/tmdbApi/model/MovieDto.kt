package com.task.data.dataSource.remote.tmdbApi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieDto(
    val id: Int,
    val title: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    val popularity: Double,

    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Int
)