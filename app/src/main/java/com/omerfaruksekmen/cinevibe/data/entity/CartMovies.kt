package com.omerfaruksekmen.cinevibe.data.entity

// The data model of the movie object in the cart from the web service.
data class CartMovies(var cartId: Int,
                      var name:String,
                      var image:String,
                      var price:Int,
                      var category:String,
                      var rating:Double,
                      var year:Int,
                      var director:String,
                      var description:String,
                      var orderAmount: Int,
                      var userName: String) {
}