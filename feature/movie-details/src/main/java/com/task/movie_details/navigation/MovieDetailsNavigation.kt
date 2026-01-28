package com.task.movie_details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.task.movie_details.MovieDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(
    val movieId: Int
)

fun NavController.navigateToMovieDetails(movieId: Int) =
    navigate(MovieDetailsRoute(movieId))

fun NavGraphBuilder.movieDetailsGraph(
    navController: NavController
) {
    composable<MovieDetailsRoute> { backStackEntry ->
        val movieId =
            backStackEntry.arguments?.getInt("movieId") ?: return@composable

        MovieDetailsScreen(
            movieId = movieId,
            onBack = navController::popBackStack
        )
    }
}