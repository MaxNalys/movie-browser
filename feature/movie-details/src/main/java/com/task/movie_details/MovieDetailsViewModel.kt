package com.task.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.data.dataSource.local.db.entity.SavedMovieEntity
import com.task.data.reposiory.MovieRepository
import com.task.data.reposiory.SavedMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedRepository: SavedMovieRepository
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

    fun isMovieSaved(movieId: Int) = savedRepository.isSaved(movieId)

    fun toggleSavedMovie(movie: com.task.model.Movie) = viewModelScope.launch {
        val entity = SavedMovieEntity(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterPath.orEmpty(),
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage.toFloat()
        )
        val saved = savedRepository.isSaved(movie.id).first()
        if (saved) savedRepository.removeMovie(entity)
        else savedRepository.saveMovie(entity)
    }

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