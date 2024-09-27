package com.akirachix.dishhub

import com.akirachix.dishhub.Vegetables
import com.akirachix.dishhub.ui.Fruits
import retrofit2.Call
import retrofit2.http.GET

interface FruitsApiService {
    @GET("api/categories/3/food-items/")
    fun getFoodItems(): Call<List<Fruits>>
}
