package com.akirachix.dishhub.api

import com.akirachix.dishhub.model.LoginRequest
import com.akirachix.dishhub.model.LoginResponse
import com.akirachix.dishhub.models.UserProfileResponse
import com.akirachix.dishhub.models.UserProfileUpdate
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.POST

interface ApiInterface {
    @POST("/api/users/login/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}

interface ApiService {
    // Fetch user profile by id
    @GET("api/user/profile/{id}/")
    fun getUserProfile(@Path("id") userId: Int): Call<UserProfileResponse>

    // Update user profile
    @PATCH("api/user/profile/update/")
    fun updateUserProfile(@Body userProfile: UserProfileUpdate): Call<Void>
}
