package com.akirachix.dishhub.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
object ApiClient {
    private const val BASE_URL = "https://dishhub-2ea9d6ca8e11.herokuapp.com/"

   val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    val api:ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

}




