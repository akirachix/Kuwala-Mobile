package com.akirachix.dishhub

import com.akirachix.dishhub.PantryApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://dishhub-2ea9d6ca8e11.herokuapp.com/"

    val pantryApiService: PantryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PantryApiService::class.java)
    }
}
