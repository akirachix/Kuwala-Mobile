package com.akirachix.dishhub.model

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
)