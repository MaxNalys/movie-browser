package com.task.data.reposiory

import com.task.model.Movie

interface MovieRepository {

    suspend fun getTrendingMovies(page: Int): List<Movie>

    suspend fun getMovieDetails(movieId: Int): Movie

}