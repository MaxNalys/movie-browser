package com.task.movie_details

sealed class MovieDetailsEvent {
    data object OnBackClick : MovieDetailsEvent()
}