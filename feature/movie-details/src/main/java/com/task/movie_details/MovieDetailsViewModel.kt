package com.task.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.data.reposiory.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieId = MutableStateFlow<Int?>(null)
    private val _event = MutableSharedFlow<MovieDetailsEvent>()
    val event: SharedFlow<MovieDetailsEvent> = _event.asSharedFlow()

    val uiState: StateFlow<MovieDetailsUiState> = _movieId
        .filterNotNull()
        .flatMapLatest { movieId ->
            flow {
                emit(MovieDetailsUiState.Loading)

                val allGenres = repository.getGenres()

                val movie = repository.getMovieDetails(movieId)

                val movieGenres = allGenres.filter { genre -> movie.genreIds.contains(genre.id) }

                emit(MovieDetailsUiState.Success(movie, movieGenres))
            }.catch { e ->
                emit(MovieDetailsUiState.Error(e.message))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieDetailsUiState.Loading
        )

    fun load(movieId: Int) {
        _movieId.value = movieId
    }

    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            MovieDetailsEvent.OnBackClick -> viewModelScope.launch {
                _event.emit(MovieDetailsEvent.OnBackClick)
            }
        }
    }
}