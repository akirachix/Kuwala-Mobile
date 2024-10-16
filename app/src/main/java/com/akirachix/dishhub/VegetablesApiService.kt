
package com.akirachix.dishhub
import RecipesResponse
import Vegetables
import retrofit2.http.Query

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/categories/1/food-items/")
    fun getFruits(): Call<List<Vegetables>>

    // Fetch Dairy food items
    @GET("api/categories/4/food-items/")  // Replace with the correct endpoint for Dairy category
    fun getDairyItems(): Call<List<Dairy>>

    // Fetch recipes based on ingredients
    @GET("api/recipes")
    fun getRecipes(@Query("ingredients") ingredientsQuery: String): Call<List<RecipesResponse>>
}

