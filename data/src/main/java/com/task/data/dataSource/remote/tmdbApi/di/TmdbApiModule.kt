package com.task.data.dataSource.remote.tmdbApi.di

import com.task.data.dataSource.remote.tmdbApi.TmdbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TmdbApiModule {

    @Provides
    @Singleton
    fun provideTmdbApiService(
        retrofit: Retrofit
    ): TmdbApiService =
        retrofit.create(TmdbApiService::class.java)
}