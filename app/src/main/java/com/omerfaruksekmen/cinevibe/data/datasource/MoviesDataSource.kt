package com.omerfaruksekmen.cinevibe.data.datasource

import com.omerfaruksekmen.cinevibe.data.entity.FavoriteMovies
import com.omerfaruksekmen.cinevibe.data.entity.Movies
import com.omerfaruksekmen.cinevibe.data.entity.CartMovies
import com.omerfaruksekmen.cinevibe.retrofit.MoviesDao
import com.omerfaruksekmen.cinevibe.room.FavoritesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/* The Data Source class directs the parameters it receives from the repository to the
relevant DAO interfaces and passes the results obtained from Retrofit or Room through the DAO back to
the repository class.*/
class MoviesDataSource(var moviesDao: MoviesDao, var favoritesDao: FavoritesDao) {

    suspend fun addMovieToCart(movieName: String, movieImage: String,
                               moviePrice: Int, movieCategory: String,
                               movieRating: Double, movieYear: Int,
                               movieDirector: String, movieDescription: String,
                               movieOrderAmount: Int, movieUsername: String){
        moviesDao.addMovieToCart(movieName, movieImage,
                                moviePrice, movieCategory,
                                movieRating, movieYear,
                                movieDirector, movieDescription,
                                movieOrderAmount, movieUsername)
    }

    suspend fun addFavorite(favoriteMovie: FavoriteMovies){
        favoritesDao.addFavorite(favoriteMovie)
    }

    suspend fun removeMovieFromCart(movieCartId: Int, movieUsername: String){
        moviesDao.removeMovieFromCart(movieCartId, movieUsername)
    }

    suspend fun deleteFavorite(movieName : String){
        favoritesDao.deleteFavorite(movieName)
    }

    suspend fun getAllMovies() : List<Movies> = withContext(Dispatchers.IO){
        return@withContext moviesDao.getAllMovies().movies
    }

    suspend fun getAllMoviesInTheCart(movieUsername: String) : List<CartMovies> = withContext(Dispatchers.IO){
        return@withContext moviesDao.getAllMoviesInTheCart(movieUsername).movie_cart
    }

    suspend fun getAllFavorites() : List<FavoriteMovies> = withContext(Dispatchers.IO){
        return@withContext favoritesDao.getAllFavorites()
    }
}