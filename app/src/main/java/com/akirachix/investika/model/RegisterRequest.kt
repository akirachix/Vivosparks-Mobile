package com.akirachix.investika.model

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val age: Int?,
    val gender: String,
    val location: String?,
    val income: Float?,
    val avatar: String
)

data class RegisterResponse(
    val status: String,
    val message: String
)
