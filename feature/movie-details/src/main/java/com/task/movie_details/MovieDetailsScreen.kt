package com.task.movie_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.component.SimpleLoadingIndicator
import com.task.ui.cards.MovieDetailCard
import com.task.movies.R

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onBack: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var movieState by remember { mutableStateOf<com.task.model.Movie?>(null) }
    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(movieId) {
        viewModel.load(movieId)
    }

    LaunchedEffect(uiState) {
        if (uiState is MovieDetailsUiState.Success) {
            val movie = (uiState as MovieDetailsUiState.Success).movie
            movieState = movie
            viewModel.isMovieSaved(movie.id)
                .collect { saved -> isSaved = saved }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            MovieDetailsUiState.Loading -> {
                SimpleLoadingIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is MovieDetailsUiState.Success -> {
                val movie = state.movie
                val genreNames = state.genres.map { it.name }

                MovieDetailCard(
                    posterPath = movie.posterPath.orEmpty(),
                    title = movie.title,
                    releaseDate = movie.releaseDate,
                    overview = movie.overview,
                    rating = movie.voteAverage.toFloat(),
                    voteCount = movie.voteCount,
                    genres = genreNames,
                    isSaved = isSaved,
                    onCardClick = {},
                    onSaveClick = { movieState?.let { viewModel.toggleSavedMovie(it) } }
                )

                IconButton(
                    onClick = { viewModel.onEvent(MovieDetailsEvent.OnBackClick); onBack() },
                    modifier = Modifier.padding(16.dp).align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            is MovieDetailsUiState.Error -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = state.message ?: stringResource(R.string.error)
                )
            }
        }
    }
}