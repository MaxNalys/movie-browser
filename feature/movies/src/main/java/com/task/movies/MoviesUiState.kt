package com.task.movies

import com.task.model.Movie

sealed class MoviesUiState {
    object Loading : MoviesUiState()

    data class Success(
        val movies: List<Movie>
    ) : MoviesUiState()

    data class Error(val message: String?) : MoviesUiState()
}