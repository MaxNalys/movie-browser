package com.task.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.component.SimpleLoadingIndicator
import com.task.designsystem.constants.Paddings.MEDIUM_PADDING
import com.task.designsystem.constants.Paddings.XX_LARGE_PADDING
import com.task.designsystem.constants.Paddings.X_LARGE_PADDING
import com.task.ui.cards.MovieCard


@Composable
fun MoviesScreen(
    onMovieClick: (com.task.model.Movie) -> Unit,
    onSeeAllClick: (MovieListType) -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is MoviesUiState.Loading -> SimpleLoadingIndicator()

            is MoviesUiState.Success -> {
                val state = uiState as MoviesUiState.Success
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(XX_LARGE_PADDING)
                ) {
                    MoviesSection(
                        title = stringResource(R.string.movie_section_trending),
                        movies = state.trending,
                        onMovieClick = onMovieClick,
                        onSeeAllClick = { onSeeAllClick(MovieListType.TRENDING) },
                        loadNextPage = { viewModel.loadNextPage(MovieListType.TRENDING) }
                    )

                    Spacer(Modifier.height(X_LARGE_PADDING))

                    MoviesSection(
                        title = stringResource(R.string.movie_section_popular),
                        movies = state.popular,
                        onMovieClick = onMovieClick,
                        onSeeAllClick = { onSeeAllClick(MovieListType.POPULAR) },
                        loadNextPage = { viewModel.loadNextPage(MovieListType.POPULAR) }
                    )
                }
            }

            is MoviesUiState.Error -> {
                val state = uiState as MoviesUiState.Error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(text = state.message ?: stringResource(R.string.error))
                }
            }
        }
    }
}

@Composable
fun MoviesSection(
    title: String,
    movies: List<com.task.model.Movie>,
    onMovieClick: (com.task.model.Movie) -> Unit,
    onSeeAllClick: () -> Unit,
    loadNextPage: () -> Unit = {}
) {
    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)
            TextButton(onClick = onSeeAllClick) { Text(stringResource(R.string.see_all_btn)) }
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)) {
            items(movies) { movie ->
                MovieCard(
                    posterPath = movie.posterPath.orEmpty(),
                    title = movie.title,
                    year = movie.releaseDate,
                    rating = movie.voteAverage.toFloat(),
                    onMovieClick = { onMovieClick(movie) }
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