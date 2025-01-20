package com.omerfaruksekmen.cinevibe.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

// Creating the favorites table in our local Room database to store the favorite movies.
@Entity(tableName = "favorites")
data class FavoriteMovies(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") @NotNull var id: Int = 0,
    @ColumnInfo(name = "image") @NotNull var image: String,
    @ColumnInfo(name = "name") @NotNull var name: String,
    @ColumnInfo(name = "rating") @NotNull var rating: Double,
    @ColumnInfo(name = "year") @NotNull var year: Int,
    @ColumnInfo(name = "director") @NotNull var director: String
) 