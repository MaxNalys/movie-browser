package com.task.moviebrowser.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MovieBrowserState(
    val navController: NavHostController
)

@Composable
fun rememberMovieBrowserState(
    navController: NavHostController = rememberNavController()
): MovieBrowserState {
    return remember(navController) {
        MovieBrowserState(navController)
    }
}