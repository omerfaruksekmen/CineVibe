package com.omerfaruksekmen.cinevibe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.omerfaruksekmen.cinevibe.ui.screen.Navigation
import com.omerfaruksekmen.cinevibe.ui.theme.CineVibeTheme
import com.omerfaruksekmen.cinevibe.ui.viewmodel.CartPageViewModel
import com.omerfaruksekmen.cinevibe.ui.viewmodel.FavoritesPageViewModel
import com.omerfaruksekmen.cinevibe.ui.viewmodel.HomePageViewModel
import com.omerfaruksekmen.cinevibe.ui.viewmodel.MovieDetailPageViewModel
import com.omerfaruksekmen.cinevibe.ui.viewmodel.SearchPageViewModel
import dagger.hilt.android.AndroidEntryPoint

// In our MainActivity class, we define the ViewModels and send them to our page transition file.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val homePageViewModel : HomePageViewModel by viewModels()
    val movieDetailPageViewModel : MovieDetailPageViewModel by viewModels()
    val cartPageViewModel : CartPageViewModel by viewModels()
    val favoritesPageViewModel : FavoritesPageViewModel by viewModels()
    val searchPageViewModel : SearchPageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineVibeTheme {
                Navigation(
                    homePageViewModel = homePageViewModel,
                    movieDetailPageViewModel = movieDetailPageViewModel,
                    cartPageViewModel = cartPageViewModel,
                    favoritesPageViewModel = favoritesPageViewModel,
                    searchPageViewModel = searchPageViewModel
                )
            }
        }
    }
}