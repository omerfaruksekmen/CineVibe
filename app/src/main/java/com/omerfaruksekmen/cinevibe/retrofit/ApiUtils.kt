package com.omerfaruksekmen.cinevibe.retrofit

// The class where we define the base URL for the web service and establish the connection with the DAO interface.
class ApiUtils {
    companion object{
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getMoviesDao() : MoviesDao{
            return RetrofitClient.getClient(BASE_URL).create(MoviesDao::class.java)
        }
    }
}