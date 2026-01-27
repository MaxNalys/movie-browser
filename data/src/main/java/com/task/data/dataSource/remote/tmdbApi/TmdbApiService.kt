package com.task.data.dataSource.remote.tmdbApi

import com.task.data.dataSource.remote.tmdbApi.model.MovieDto
import com.task.data.dataSource.remote.tmdbApi.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "week",
        @Query("page") page: Int
    ): MovieResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDto
}