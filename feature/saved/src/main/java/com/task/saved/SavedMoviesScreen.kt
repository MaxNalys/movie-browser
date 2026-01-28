package com.task.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.constants.Paddings.LARGE_PADDING
import com.task.ui.cards.MovieCard

@Composable
fun SavedMoviesScreen(
    onBack: () -> Unit,
    onMovieClick: (Int) -> Unit,
    viewModel: SavedMoviesViewModel = hiltViewModel()
) {
    val savedMovies by viewModel.savedMovies.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(LARGE_PADDING)
                .align(Alignment.TopStart)
                .zIndex(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        if (savedMovies.isEmpty()) {
            Text(
                text = "No saved movies",
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 45.dp,
                    start = 50.dp,
                    end = LARGE_PADDING,
                    bottom = LARGE_PADDING),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(savedMovies, key = { it.id }) { movie ->
                    MovieCard(
                        posterPath = movie.posterPath,
                        title = movie.title,
                        year = movie.releaseDate,
                        rating = movie.voteAverage.toFloat(),
                        isSaved = true,
                        onMovieClick = { onMovieClick(movie.id) },
                        onSaveClick = { viewModel.removeMovie(movie) })
                }
            }
        }
    }
}
