package com.omerfaruksekmen.cinevibe.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.omerfaruksekmen.cinevibe.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/* Our ViewModel class that connects with the movie detail page and facilitates the transition
between the UI and data layer. It accesses the repository for operations to be performed when
called from the page.*/
@HiltViewModel
class MovieDetailPageViewModel @Inject constructor(var moviesRepository: MoviesRepository) : ViewModel() {

    fun addMovieToCart(movieName: String, movieImage: String,
                moviePrice: Int, movieCategory: String,
                movieRating: Double, movieYear: Int,
                movieDirector: String, movieDescription: String,
                movieOrderAmount: Int, movieUsername: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val moviesInTheCart = moviesRepository.getAllMoviesInTheCart(movieUsername)
                val sameMovie = moviesInTheCart.find { it.name == movieName }

                if (sameMovie != null) {
                    moviesRepository.removeMovieFromCart(sameMovie.cartId, movieUsername)

                    moviesRepository.addMovieToCart(movieName, movieImage,
                        moviePrice, movieCategory,
                        movieRating, movieYear,
                        movieDirector, movieDescription,
                        sameMovie.orderAmount + movieOrderAmount, movieUsername)
                } else {
                    moviesRepository.addMovieToCart(movieName, movieImage,
                        moviePrice, movieCategory,
                        movieRating, movieYear,
                        movieDirector, movieDescription,
                        movieOrderAmount, movieUsername)
                }
            }catch (e: Exception) {
                moviesRepository.addMovieToCart(movieName, movieImage,
                    moviePrice, movieCategory,
                    movieRating, movieYear,
                    movieDirector, movieDescription,
                    movieOrderAmount, movieUsername)
            }

        }
    }
}