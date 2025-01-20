package com.omerfaruksekmen.cinevibe.data.entity

// The response model for the list of movies in the cart returned from the web service.
data class CartMoviesResponse(var movie_cart: List<CartMovies>) {
}