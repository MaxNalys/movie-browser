package com.task.movie_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.task.movies.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.component.SimpleLoadingIndicator
import com.task.ui.cards.MovieDetailCard

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onBack: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.load(movieId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {

            MovieDetailsUiState.Loading -> {
                SimpleLoadingIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is MovieDetailsUiState.Success -> {

                val genreNames = state.genres.map { it.name }

                MovieDetailCard(
                    posterPath = state.movie.posterPath.orEmpty(),
                    title = state.movie.title,
                    releaseDate = state.movie.releaseDate,
                    overview = state.movie.overview,
                    rating = state.movie.voteAverage.toFloat(),
                    voteCount = state.movie.voteCount,
                    genres = genreNames,
                    onCardClick = {}

                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(MovieDetailsEvent.OnBackClick)
                        onBack()
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
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
