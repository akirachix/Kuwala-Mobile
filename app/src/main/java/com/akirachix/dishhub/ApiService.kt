package com.akirachix.dishhub

import com.akirachix.dishhub.IngredientDetail
import com.akirachix.dishhub.PantryDetail
import com.akirachix.dishhub.PantryItems

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PantryApiService {
    @GET("api/pantry/")
    fun getPantryItems(): Call<List<PantryItems>>

//    @GET("api/pantry/{id}/")
//    fun getPantryDetail(@Path("id") id: Int): Call<PantryDetail>
//
//    @GET("api/pantry/ingredients/{id}/")
//    fun getIngredientDetailInPantry(@Path("id") id: Int): Call<IngredientDetail>
}