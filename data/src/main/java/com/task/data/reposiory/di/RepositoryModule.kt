package com.task.data.reposiory.di

import com.task.data.reposiory.DefaultMovieRepository
import com.task.data.reposiory.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(
        impl: DefaultMovieRepository
    ): MovieRepository
}