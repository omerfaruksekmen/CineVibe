package com.omerfaruksekmen.cinevibe.di

import android.content.Context
import androidx.room.Room
import com.omerfaruksekmen.cinevibe.data.datasource.MoviesDataSource
import com.omerfaruksekmen.cinevibe.data.repo.MoviesRepository
import com.omerfaruksekmen.cinevibe.retrofit.ApiUtils
import com.omerfaruksekmen.cinevibe.retrofit.MoviesDao
import com.omerfaruksekmen.cinevibe.room.FavoritesDao
import com.omerfaruksekmen.cinevibe.room.DatabaseRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// The class we created to send parameters to the relevant classes during dependency injection operations.
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(moviesDataSource: MoviesDataSource) : MoviesRepository {
        return MoviesRepository(moviesDataSource)
    }

    @Provides
    @Singleton
    fun provideMoviesDataSource(moviesDao: MoviesDao, favoritesDao: FavoritesDao) : MoviesDataSource {
        return MoviesDataSource(moviesDao, favoritesDao)
    }

    @Provides
    @Singleton
    fun provideMoviesDao() : MoviesDao {
        return ApiUtils.getMoviesDao()
    }

    // The class where we create our Room database. The movies in the assets folder are based on an SQLite database.
    @Provides
    @Singleton
    fun provideFavoritesDao(@ApplicationContext context: Context) : FavoritesDao {
        val databaseRoom = Room.databaseBuilder(context, DatabaseRoom::class.java,"movies.sqlite")
            .createFromAsset("movies.sqlite").build()
        return databaseRoom.getFavoritesDao()
    }
}