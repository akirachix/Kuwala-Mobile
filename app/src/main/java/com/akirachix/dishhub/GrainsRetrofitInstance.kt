package com.akirachix.dishhub

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GrainsRetrofitInstance {

        private const val BASE_URL = "https://dishhub-2ea9d6ca8e11.herokuapp.com/"

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: GrainsApiService by lazy {
            retrofit.create(GrainsApiService::class.java)
        }
    }