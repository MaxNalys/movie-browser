package com.task.moviebrowser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.data.reposiory.MovieRepository
import com.task.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            runCatching {
                repository.getTrendingMovies(1)
            }.onSuccess {
                _movies.value = it
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}
