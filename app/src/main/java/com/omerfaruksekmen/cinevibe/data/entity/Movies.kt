package com.omerfaruksekmen.cinevibe.data.entity

// The data model of the movie object in the web service.
data class Movies(var id:Int,
                  var name:String,
                  var image:String,
                  var price:Int,
                  var category:String,
                  var rating:Double,
                  var year:Int,
                  var director:String,
                  var description:String) {
}