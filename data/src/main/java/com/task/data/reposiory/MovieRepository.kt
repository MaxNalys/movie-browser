package com.task.data.reposiory

import com.task.model.Genre
import com.task.model.Movie

interface MovieRepository {

    suspend fun getTrendingMovies(page: Int): List<Movie>

    suspend fun getPopularMovies(page: Int): List<Movie>

    suspend fun getMovieDetails(movieId: Int): Movie

    suspend fun getGenres(): List<Genre>
}