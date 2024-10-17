package com.akirachix.dishhub.api

import RecipesResponse
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



//
    @GET("search")
    fun getRecipesByIngredients(
        @Query("q") ingredients: String,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String
    ): Call<EdamamApiResponse>
    }

//    interface YummyApiService {
//        @GET("recipes/findByIngredients")
//        fun getRecipesByIngredients(@Query("ingredients") ingredientsQuery: String): Call<RecipesResponse>
//    }




