package com.omerfaruksekmen.cinevibe.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omerfaruksekmen.cinevibe.data.entity.CartMovies
import com.omerfaruksekmen.cinevibe.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/* Our ViewModel class that connects with the cart page and facilitates the transition
between the UI and data layer.*/
// It accesses the repository for operations to be performed when called from the page.
@HiltViewModel
class CartPageViewModel @Inject constructor(var moviesRepository: MoviesRepository) : ViewModel() {
    var movieListInCart = MutableLiveData<List<CartMovies>>(emptyList())
    var isEmptyCart = MutableLiveData<Boolean>(true)

    init {
        getAllMoviesInTheCart("Enter your username here.")
    }

    fun removeMovieFromCart(movieCartId: Int,
                        movieUsername: String){
        CoroutineScope(Dispatchers.Main).launch {
            moviesRepository.removeMovieFromCart(movieCartId, movieUsername)
            getAllMoviesInTheCart("Enter your username here.")
        }
    }

    fun getAllMoviesInTheCart(movieUsername: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val movies = moviesRepository.getAllMoviesInTheCart(movieUsername)
                movieListInCart.value = movies
                isEmptyCart.value = movies.isEmpty()
            }catch (e: Exception){
                movieListInCart.value = emptyList()
                isEmptyCart.value = true
            }
        }
    }

}