package com.task.moviebrowser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.data.reposiory.MovieRepository
import com.task.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            try {
                val trending = repository.getTrendingMovies(1)
                val popular = repository.getPopularMovies(1)
                _movies.value = (trending + popular).distinctBy { it.id }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}