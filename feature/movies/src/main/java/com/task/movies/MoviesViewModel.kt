package com.task.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.data.reposiory.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var page = 1
    private var endReached = false

    init {
        loadTrending()
    }

    private fun loadTrending() {
        viewModelScope.launch {
            _uiState.value = MoviesUiState.Loading

            runCatching {
                repository.getTrendingMovies(page)
            }.onSuccess { movies ->
                _uiState.value = MoviesUiState.Success(movies)
            }.onFailure {
                _uiState.value = MoviesUiState.Error(it.message)
            }
        }
    }

    fun loadNextPage() {
        if (endReached) return

        viewModelScope.launch {
            page++

            runCatching {
                repository.getTrendingMovies(page)
            }.onSuccess { movies ->
                if (movies.isEmpty()) {
                    endReached = true
                    return@onSuccess
                }

                val current =
                    (_uiState.value as? MoviesUiState.Success)?.movies.orEmpty()

                _uiState.value =
                    MoviesUiState.Success(current + movies)
            }
        }
    }
}