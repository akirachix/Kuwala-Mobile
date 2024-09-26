package com.akirachix.dishhub.api

import com.akirachix.dishhub.model.LoginRequest
import com.akirachix.dishhub.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/api/users/login/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}


