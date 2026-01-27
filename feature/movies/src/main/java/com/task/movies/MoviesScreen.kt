package com.task.movies

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.task.model.Movie
import com.task.ui.cards.MovieCard

@Composable
fun MoviesScreen(
    onMovieClick: (Movie) -> Unit,
    onSeeAllClick: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize()) {
        when (uiState) {

            MoviesUiState.Loading -> {
                SimpleLoadingIndicator()
            }

            is MoviesUiState.Error -> {
                Text(
                    text = (uiState as MoviesUiState.Error).message
                        ?: stringResource(R.string.error)
                )
            }

            is MoviesUiState.Success -> {
                MoviesSection(
                    title = stringResource(R.string.movie_section_trending),
                    movies = (uiState as MoviesUiState.Success).movies,
                    onMovieClick = onMovieClick,
                    onSeeAllClick = onSeeAllClick,
                    loadNextPage = viewModel::loadNextPage
                )
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
    loadNextPage: () -> Unit
) {
    Column(
        Modifier.padding(XX_LARGE_PADDING)
    ) {

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)

            Text(
                text = stringResource(R.string.see_all_btn),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }

        Spacer(Modifier.height(MEDIUM_PADDING))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
        ) {
            items(movies) { movie ->
                MovieCard(
                    posterUrl = movie.posterPath.orEmpty(),
                    title = movie.title,
                    overview = movie.overview,
                    isSaved = false,
                    onMovieClick = { onMovieClick(movie) },
                    onSaveClick = {}
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