package com.task.movies

sealed class MoviesEvent {
    data class OnMovieClick(val movieId: Int) : MoviesEvent()
    data class OnSeeAllClick(val type: MovieListType) : MoviesEvent()
}

enum class MovieListType {
    TRENDING,
    POPULAR
}