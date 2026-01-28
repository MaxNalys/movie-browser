package com.task.moviebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.task.designsystem.theme.MovieBrowserTheme
import com.task.moviebrowser.navigation.MovieAppScreen
import com.task.moviebrowser.ui.rememberMovieAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appState = rememberMovieAppState()

            CompositionLocalProvider {
                MovieBrowserTheme {
                    MovieAppScreen(appState = appState)
                }
            }
        }
    }
}