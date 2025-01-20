package com.omerfaruksekmen.cinevibe.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* The class where we create the Retrofit object for web service operations, with the base URL
information coming from API Utils.*/
class RetrofitClient {
    companion object{
        fun getClient(baseUrl : String) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}