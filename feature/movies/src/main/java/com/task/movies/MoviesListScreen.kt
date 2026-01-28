package com.task.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.component.SimpleLoadingIndicator
import com.task.designsystem.constants.Paddings.LARGE_PADDING
import com.task.ui.cards.MovieCard

@Composable
fun MoviesListScreen(
    type: MovieListType,
    onMovieClick: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {

        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(LARGE_PADDING).align(Alignment.TopStart).zIndex(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        when (uiState) {
            is MoviesUiState.Loading -> SimpleLoadingIndicator(modifier = Modifier.align(Alignment.Center))

            is MoviesUiState.Success -> {
                val state = uiState as MoviesUiState.Success
                val movies = if (type == MovieListType.TRENDING) state.trending else state.popular

                LaunchedEffect(listState) {
                    snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .collect { lastVisibleIndex ->
                            if (lastVisibleIndex != null && lastVisibleIndex >= movies.size - 3) {
                                viewModel.loadNextPage(type)
                            }
                        }
                }

                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(
                        top = 45.dp,
                        start = 50.dp,
                        end = LARGE_PADDING,
                        bottom = LARGE_PADDING
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movies, key = { it.id }) { movie ->
                        val isSaved by viewModel.isMovieSaved(movie.id)
                            .collectAsState(initial = false)

                        MovieCard(
                            posterPath = movie.posterPath.orEmpty(),
                            title = movie.title,
                            year = movie.releaseDate,
                            rating = movie.voteAverage.toFloat(),
                            isSaved = isSaved,
                            onMovieClick = { onMovieClick(movie.id) },
                            onSaveClick = { viewModel.toggleSavedMovie(movie) }
                        )
                    }
                }
            }

            is MoviesUiState.Error -> {
                val state = uiState as MoviesUiState.Error
                Text(
                    text = state.message ?: stringResource(R.string.error),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}