package com.task.saved.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.task.saved.SavedMoviesScreen


fun NavGraphBuilder.savedMoviesGraph(
    navController: NavHostController,
    onMovieClick: (Int) -> Unit
) {
    composable("saved") {
        SavedMoviesScreen(
            onBack = {
                navController.popBackStack()
            },
            onMovieClick = onMovieClick
        )
    }
}