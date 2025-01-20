package com.omerfaruksekmen.cinevibe.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.omerfaruksekmen.cinevibe.data.entity.Movies
import com.omerfaruksekmen.cinevibe.ui.viewmodel.HomePageViewModel
import com.omerfaruksekmen.cinevibe.ui.viewmodel.SearchPageViewModel
import com.omerfaruksekmen.cinevibe.ui.viewmodel.FavoritesPageViewModel
import com.omerfaruksekmen.cinevibe.ui.viewmodel.MovieDetailPageViewModel
import com.omerfaruksekmen.cinevibe.ui.viewmodel.CartPageViewModel

// Navigation Page
@Composable
fun Navigation(homePageViewModel: HomePageViewModel,
               movieDetailPageViewModel: MovieDetailPageViewModel,
               cartPageViewModel: CartPageViewModel,
               favoritesPageViewModel: FavoritesPageViewModel,
               searchPageViewModel: SearchPageViewModel
){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "homePage"){
        composable("homePage"){
            HomePage(navController = navController, homePageViewModel = homePageViewModel)
        }
        composable("movieDetailPage/{movie}",
            arguments = listOf(navArgument("movie"){ type = NavType.StringType })
        ){
            val json = it.arguments?.getString("movie")
            val movieObject = Gson().fromJson(json, Movies::class.java)
            MovieDetailPage(incomingMovieObject = movieObject, navController = navController, movieDetailPageViewModel = movieDetailPageViewModel, favoritesPageViewModel = favoritesPageViewModel)
        }
        composable("cartPage"){
            CartPage(navController = navController, cartPageViewModel = cartPageViewModel)
        }
        composable("favoritesPage"){
            FavoritesPage(navController = navController, favoritesPageViewModel = favoritesPageViewModel)
        }
        composable("searchPage"){
            SearchPage(navController = navController, searchPageViewModel = searchPageViewModel)
        }
    }

}