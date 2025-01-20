package com.omerfaruksekmen.cinevibe.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omerfaruksekmen.cinevibe.data.entity.FavoriteMovies
import com.omerfaruksekmen.cinevibe.data.entity.Movies
import com.omerfaruksekmen.cinevibe.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Our viewmodel class that connects with the Favorites page and facilitates the transition between the UI and data layer.
// It accesses the repository for operations to be performed when called from the page.
@HiltViewModel
class FavoritesPageViewModel @Inject constructor(var moviesRepository: MoviesRepository) : ViewModel() {
    var favoritesList = MutableLiveData<List<FavoriteMovies>>()
    var favoriteStatus = MutableLiveData<Boolean>()

    fun getAllFavorites() {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesList.value = moviesRepository.getAllFavorites()
        }
    }

    fun addFavorite(movie: Movies) {
        val favoriteMovie = FavoriteMovies(
            image = movie.image,
            name = movie.name,
            rating = movie.rating,
            year = movie.year,
            director = movie.director
        )
        CoroutineScope(Dispatchers.Main).launch {
            moviesRepository.addFavorite(favoriteMovie)
            favoriteStatus.value = true
        }
    }

    fun deleteFavorite(movieName : String) {
        CoroutineScope(Dispatchers.Main).launch {
            moviesRepository.deleteFavorite(movieName)
            getAllFavorites()
            favoriteStatus.value = false
        }
    }

    fun favoriteControl(movieName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val favoriteMovies = moviesRepository.getAllFavorites()
            val sameFavoriteMovie = favoriteMovies.find { it.name == movieName }

            favoriteStatus.value = sameFavoriteMovie != null
        }
    }
} 