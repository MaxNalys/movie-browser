package com.task.moviebrowser.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.task.designsystem.component.MovieBottomBar
import com.task.movie_details.navigation.movieDetailsGraph
import com.task.movie_details.navigation.navigateToMovieDetails
import com.task.moviebrowser.ui.MovieAppState
import com.task.movies.navigation.MoviesRoute
import com.task.movies.navigation.moviesGraph
import com.task.saved.navigation.savedMoviesGraph


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

        savedMoviesGraph(
            navController = appState.navController,
            onMovieClick = { movieId ->
                appState.navController.navigateToMovieDetails(movieId)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppScreen(appState: MovieAppState) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Movie Browser") })
        },
        bottomBar = {
            MovieBottomBar(
                onHomeClick = {
                    appState.navController.navigate(MoviesRoute) {
                        popUpTo(MoviesRoute) { inclusive = false }
                    }
                },
                onSavedClick = {
                    appState.navController.navigate("saved") {
                        popUpTo(MoviesRoute) { inclusive = false }
                    }
                }
            )
        }
    ) { padding ->
        MovieAppNavHost(
            appState = appState,
            modifier = Modifier.padding(padding)
        )
    }
}