package com.task.movies

import com.task.model.Movie

sealed class MoviesUiState {
    data class Success(
        val trending: List<Movie> = emptyList(),
        val popular: List<Movie> = emptyList()
    ) : MoviesUiState()

    object Loading : MoviesUiState()

    data class Error(val message: String?) : MoviesUiState()
}