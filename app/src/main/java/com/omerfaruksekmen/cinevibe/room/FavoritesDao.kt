package com.omerfaruksekmen.cinevibe.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.omerfaruksekmen.cinevibe.data.entity.FavoriteMovies

// The DAO interface where we define queries for the Room database.
@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteMovies>

    @Insert
    suspend fun addFavorite(favoriteMovie: FavoriteMovies)

    @Query("DELETE FROM favorites WHERE name = :movieName")
    suspend fun deleteFavorite(movieName : String)

} 