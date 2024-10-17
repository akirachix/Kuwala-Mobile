

package com.akirachix.dishhub

import retrofit2.Call
import retrofit2.http.GET

interface GrainsApiService {
    @GET("api/categories/2/food-items/")
    fun getFoodItems(): Call<List<Grains>>
}