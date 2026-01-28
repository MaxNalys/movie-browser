package com.task.movies.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.task.movies.MovieListType
import com.task.movies.MoviesListScreen
import com.task.movies.MoviesScreen
import kotlinx.serialization.Serializable

@Serializable
data object MoviesRoute

@Serializable
data class MoviesListRoute(
    val type: MovieListType
)

fun NavController.navigateToMovies() =
    navigate(MoviesRoute)

fun NavController.navigateToMoviesList(type: MovieListType) =
    navigate(MoviesListRoute(type))

fun NavGraphBuilder.moviesGraph(
    navController: NavController,
    onMovieClick: (Int) -> Unit
) {
    composable<MoviesRoute> {
        MoviesScreen(
            onMovieClick = { movie ->
                onMovieClick(movie.id)
            },
            onSeeAllClick = { type ->
                navController.navigateToMoviesList(type)
            }
        )
    }

    composable<MoviesListRoute> { backStackEntry ->
        val type = backStackEntry.arguments
            ?.getSerializable("type") as? MovieListType
            ?: MovieListType.TRENDING

        MoviesListScreen(
            type = type,
            onMovieClick = onMovieClick,
            onBack = navController::popBackStack
        )
    }
}