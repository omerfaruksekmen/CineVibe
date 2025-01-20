package com.omerfaruksekmen.cinevibe.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omerfaruksekmen.cinevibe.data.entity.FavoriteMovies

@Database(entities = [FavoriteMovies::class], version = 1)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun getFavoritesDao(): FavoritesDao
}