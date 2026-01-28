package com.task.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.data.dataSource.local.db.entity.SavedMovieEntity
import com.task.data.reposiory.SavedMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMoviesViewModel @Inject constructor(
    private val savedRepository: SavedMovieRepository
) : ViewModel() {

    val savedMovies: StateFlow<List<SavedMovieEntity>> = savedRepository.getSavedMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun removeMovie(movie: SavedMovieEntity) = viewModelScope.launch {
        savedRepository.removeMovie(movie)
    }
}