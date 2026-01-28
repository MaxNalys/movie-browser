package com.task.moviebrowser.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class MovieAppState(
    val navController: NavHostController
)

@Composable
fun rememberMovieAppState(
    navController: NavHostController = rememberNavController()
): MovieAppState {
    return remember(navController) {
        MovieAppState(navController)
    }
}