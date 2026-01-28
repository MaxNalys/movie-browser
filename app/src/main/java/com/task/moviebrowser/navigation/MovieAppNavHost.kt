package com.task.moviebrowser.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.task.movie_details.navigation.movieDetailsGraph
import com.task.movie_details.navigation.navigateToMovieDetails
import com.task.moviebrowser.ui.MovieAppState
import com.task.movies.navigation.MoviesRoute
import com.task.movies.navigation.moviesGraph


@Composable
fun MovieAppNavHost(
    appState: MovieAppState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = MoviesRoute,
        modifier = modifier
    ) {

        moviesGraph(
            navController = appState.navController,
            onMovieClick = { movieId ->
                appState.navController.navigateToMovieDetails(movieId)
            }
        )

        movieDetailsGraph(
            navController = appState.navController
        )

    }
}