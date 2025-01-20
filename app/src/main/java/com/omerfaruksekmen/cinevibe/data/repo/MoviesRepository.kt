package com.omerfaruksekmen.cinevibe.data.repo

import com.omerfaruksekmen.cinevibe.data.datasource.MoviesDataSource
import com.omerfaruksekmen.cinevibe.data.entity.FavoriteMovies
import com.omerfaruksekmen.cinevibe.data.entity.Movies
import com.omerfaruksekmen.cinevibe.data.entity.CartMovies

/* Our Repository class forwards the parameters it receives from the ViewModel in the
UI layer to the Data Source.*/
class MoviesRepository(var moviesDataSource: MoviesDataSource) {

    suspend fun addMovieToCart(movieName: String, movieImage: String,
                               moviePrice: Int, movieCategory: String,
                               movieRating: Double, movieYear: Int,
                               movieDirector: String, movieDescription: String,
                               movieOrderAmount: Int, movieUsername: String)
    = moviesDataSource.addMovieToCart(movieName, movieImage,
                        moviePrice, movieCategory,
                        movieRating, movieYear,
                        movieDirector, movieDescription,
                        movieOrderAmount, movieUsername)

    suspend fun addFavorite(favoriteMovie: FavoriteMovies) = moviesDataSource.addFavorite(favoriteMovie)

    suspend fun removeMovieFromCart(movieCartId: Int,
                                    movieUsername: String)
    = moviesDataSource.removeMovieFromCart(movieCartId, movieUsername)

    suspend fun deleteFavorite(movieName : String) = moviesDataSource.deleteFavorite(movieName)

    suspend fun getAllMovies() : List<Movies> = moviesDataSource.getAllMovies()

    suspend fun getAllMoviesInTheCart(movieUsername: String) : List<CartMovies>
    = moviesDataSource.getAllMoviesInTheCart(movieUsername)

    suspend fun getAllFavorites() : List<FavoriteMovies> = moviesDataSource.getAllFavorites()

}