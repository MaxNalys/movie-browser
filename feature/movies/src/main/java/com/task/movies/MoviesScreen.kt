package com.task.movies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.component.SimpleLoadingIndicator
import com.task.designsystem.constants.Paddings.MEDIUM_PADDING
import com.task.designsystem.constants.Paddings.XX_LARGE_PADDING
import com.task.designsystem.constants.Paddings.X_LARGE_PADDING
import com.task.model.Movie
import com.task.ui.cards.MovieCard

@Composable
fun MoviesScreen(
    onMovieClick: (Movie) -> Unit,
    onSeeAllClick: (MovieListType) -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is MoviesUiState.Loading -> SimpleLoadingIndicator()

        is MoviesUiState.Success -> {
            val state = uiState as MoviesUiState.Success
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(XX_LARGE_PADDING),
                verticalArrangement = Arrangement.spacedBy(X_LARGE_PADDING)
            ) {
                item {
                    MoviesSection(
                        title = stringResource(R.string.movie_section_trending),
                        movies = state.trending,
                        onMovieClick = onMovieClick,
                        onSeeAllClick = { onSeeAllClick(MovieListType.TRENDING) },
                        loadNextPage = { viewModel.loadNextPage(MovieListType.TRENDING) },
                        viewModel = viewModel
                    )
                }

                item {
                    MoviesSection(
                        title = stringResource(R.string.movie_section_popular),
                        movies = state.popular,
                        onMovieClick = onMovieClick,
                        onSeeAllClick = { onSeeAllClick(MovieListType.POPULAR) },
                        loadNextPage = { viewModel.loadNextPage(MovieListType.POPULAR) },
                        viewModel = viewModel
                    )
                }
            }
        }

        is MoviesUiState.Error -> {
            val state = uiState as MoviesUiState.Error
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.message ?: stringResource(R.string.error))
            }
        }
    }
}
@Composable
fun MoviesSection(
    title: String,
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onSeeAllClick: () -> Unit,
    loadNextPage: () -> Unit = {},
    viewModel: MoviesViewModel
) {
    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)
            TextButton(onClick = onSeeAllClick) {
                Text(stringResource(R.string.see_all_btn))
            }
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)) {
            items(movies) { movie ->
                // 🔹 Локальний стан сердечка
                val isSavedFlow = viewModel.isMovieSaved(movie.id)
                val isSaved by isSavedFlow.collectAsState(initial = false)

                MovieCard(
                    posterPath = movie.posterPath.orEmpty(),
                    title = movie.title,
                    year = movie.releaseDate,
                    rating = movie.voteAverage.toFloat(),
                    isSaved = isSaved,
                    onMovieClick = { onMovieClick(movie) },
                    onSaveClick = { viewModel.toggleSavedMovie(movie) }
                )
            }

            item {
                LaunchedEffect(movies.size) {
                    loadNextPage()
                }
            }
        }
    }
}