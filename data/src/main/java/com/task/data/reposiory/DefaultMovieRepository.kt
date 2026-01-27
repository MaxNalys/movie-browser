package com.task.data.reposiory

import com.task.data.dataSource.remote.tmdbApi.TmdbApiService
import com.task.data.dataSource.remote.tmdbApi.mapper.toDomain
import com.task.model.Movie
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DefaultMovieRepository @Inject constructor(
    private val api: TmdbApiService
) : MovieRepository {

    override suspend fun getTrendingMovies(page: Int): List<Movie> =
        api.getTrendingMovies(page = page)
            .results
            .map { it.toDomain() }


    override suspend fun getMovieDetails(movieId: Int): Movie =
        api.getMovieDetails(movieId).toDomain()

}