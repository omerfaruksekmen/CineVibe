package com.omerfaruksekmen.cinevibe.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omerfaruksekmen.cinevibe.data.entity.Movies
import com.omerfaruksekmen.cinevibe.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/* Our ViewModel class that connects with the search page and facilitates the transition between
the UI and data layer.*/
// It accesses the repository for operations to be performed when called from the page.
@HiltViewModel
class SearchPageViewModel @Inject constructor(var moviesRepository: MoviesRepository) : ViewModel() {
    var movieList = MutableLiveData<List<Movies>>()
    var searchWord = MutableStateFlow("")

    fun getAllMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            movieList.value = moviesRepository.getAllMovies()
        }
    }

    fun searchMovie(searchWord: String) {
        this.searchWord.value = searchWord
    }
} 