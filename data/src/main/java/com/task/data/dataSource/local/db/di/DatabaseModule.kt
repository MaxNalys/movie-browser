package com.task.data.dataSource.local.db.di

import android.content.Context
import androidx.room.Room
import com.task.data.dataSource.local.db.MovieAppDatabase
import com.task.data.dataSource.local.db.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MovieAppDatabase =
        Room.databaseBuilder(
            context,
            MovieAppDatabase::class.java,
            "movie_app_db"
        ).build()

    @Provides
    fun provideMovieDao(db: MovieAppDatabase): MovieDao = db.movieDao()
}