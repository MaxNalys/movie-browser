package com.task.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.data.dataSource.local.db.entity.SavedMovieEntity
import com.task.data.reposiory.MovieRepository
import com.task.data.reposiory.SavedMovieRepository
import com.task.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedRepository: SavedMovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    private var trendingPage = 1
    private var popularPage = 1
    private var trendingEndReached = false
    private var popularEndReached = false

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = MoviesUiState.Loading
            runCatching {
                val trending = repository.getTrendingMovies(trendingPage)
                val popular = repository.getPopularMovies(popularPage)
                trending to popular
            }.onSuccess { (trending, popular) ->
                _uiState.value = MoviesUiState.Success(trending, popular)
            }.onFailure { e ->
                _uiState.value = MoviesUiState.Error(e.message)
            }
        }
    }

    fun loadNextPage(type: MovieListType) {
        viewModelScope.launch {
            when (type) {
                MovieListType.TRENDING -> {
                    if (trendingEndReached) return@launch
                    trendingPage++
                    runCatching { repository.getTrendingMovies(trendingPage) }
                        .onSuccess { movies ->
                            if (movies.isEmpty()) trendingEndReached = true
                            val current = (_uiState.value as? MoviesUiState.Success)?.trending ?: emptyList()
                            val popular = (_uiState.value as? MoviesUiState.Success)?.popular ?: emptyList()
                            _uiState.value = MoviesUiState.Success(current + movies, popular)
                        }
                }
                MovieListType.POPULAR -> {
                    if (popularEndReached) return@launch
                    popularPage++
                    runCatching { repository.getPopularMovies(popularPage) }
                        .onSuccess { movies ->
                            if (movies.isEmpty()) popularEndReached = true
                            val current = (_uiState.value as? MoviesUiState.Success)?.popular ?: emptyList()
                            val trending = (_uiState.value as? MoviesUiState.Success)?.trending ?: emptyList()
                            _uiState.value = MoviesUiState.Success(trending, current + movies)
                        }
                }
            }
        }
    }

    fun isMovieSaved(movieId: Int) = savedRepository.isSaved(movieId)

    fun toggleSavedMovie(movie: Movie) = viewModelScope.launch {
        val entity = SavedMovieEntity(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterPath.orEmpty(),
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage.toFloat()
        )

        val isSaved = savedRepository.isSaved(movie.id)
            .first()
        if (isSaved) savedRepository.removeMovie(entity)
        else savedRepository.saveMovie(entity)
    }
}