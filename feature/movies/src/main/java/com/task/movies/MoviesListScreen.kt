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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.component.SimpleLoadingIndicator
import com.task.designsystem.constants.Paddings.LARGE_PADDING
import com.task.model.Movie
import com.task.ui.cards.MovieCard

@Composable
fun MoviesListScreen(
    onMovieClick: (Movie) -> Unit,
    onBack: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Box(Modifier.fillMaxSize()) {

        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(LARGE_PADDING)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }

        when (uiState) {

            MoviesUiState.Loading -> {
                SimpleLoadingIndicator(Modifier.align(Alignment.Center))
            }

            is MoviesUiState.Success -> {
                val movies =
                    (uiState as MoviesUiState.Success).movies

                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(
                        top = 72.dp,
                        start = LARGE_PADDING,
                        end = LARGE_PADDING,
                        bottom = LARGE_PADDING
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movies, key = { it.id }) { movie ->
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
                        LaunchedEffect(listState) {
                            snapshotFlow {
                                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                            }.collect { index ->
                                if (index != null && index >= movies.size - 3) {
                                    viewModel.loadNextPage()
                                }
                            }
                        }
                    }
                }
            }

            is MoviesUiState.Error -> {
                Text(text = stringResource(R.string.error))
            }
        }
    }
}