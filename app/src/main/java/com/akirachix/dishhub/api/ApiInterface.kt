package com.akirachix.dishhub.api

import com.akirachix.dishhub.model.RegisterRequest
import com.akirachix.dishhub.model.RegisterResponse
import com.akirachix.dishhub.models.UserProfileResponse
import com.akirachix.dishhub.models.UserProfileUpdate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/users/register/")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @GET("api/user/profile/{id}/")
    fun getUserProfile(@Path("id") userId: Int): Call<UserProfileResponse>

    @PATCH("api/user/profile/update/")
    fun updateUserProfile(@Body userProfile: UserProfileUpdate): Call<Void>

    @GET("recipes/findByIngredients")
    fun getRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("apiKey") apiKey: String = "07d5c6ca83604b57aef6a720e80f378b"
    ): Call<List<RecipesResponse>>

    @GET("recipes/{id}/information")
    fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = "07d5c6ca83604b57aef6a720e80f378b"
    ): Call<RecipeInformation>
}
