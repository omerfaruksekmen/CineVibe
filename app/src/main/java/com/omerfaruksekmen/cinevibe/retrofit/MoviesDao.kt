package com.omerfaruksekmen.cinevibe.retrofit

import com.omerfaruksekmen.cinevibe.data.entity.CRUDResponse
import com.omerfaruksekmen.cinevibe.data.entity.MoviesResponse
import com.omerfaruksekmen.cinevibe.data.entity.CartMoviesResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

// The class where we define web service requests and provide their parameters.
interface MoviesDao {
    // http://kasimadalan.pe.hu/ -> base url

    @GET("movies/getAllMovies.php")
    suspend fun getAllMovies() : MoviesResponse

    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun addMovieToCart(@Field("name") name: String,
                               @Field("image") image: String,
                               @Field("price") price: Int,
                               @Field("category") category: String,
                               @Field("rating") rating: Double,
                               @Field("year") year: Int,
                               @Field("director") director: String,
                               @Field("description") description: String,
                               @Field("orderAmount") orderAmount: Int,
                               @Field("userName") userName: String) : CRUDResponse

    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getAllMoviesInTheCart(@Field("userName") userName: String) : CartMoviesResponse

    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun removeMovieFromCart(@Field("cartId") cartId : Int,
                                    @Field("userName") userName: String) : CRUDResponse
}