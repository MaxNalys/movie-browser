package com.task.movie_details

import com.task.model.Genre
import com.task.model.Movie


sealed class MovieDetailsUiState {

    data object Loading : MovieDetailsUiState()

    data class Success(
        val movie: Movie,
        val genres: List<Genre>
    ) : MovieDetailsUiState()

    data class Error(
        val message: String?
    ) : MovieDetailsUiState()
}