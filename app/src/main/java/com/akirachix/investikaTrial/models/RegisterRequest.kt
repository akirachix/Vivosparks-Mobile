package com.akirachix.investikaTrial.models

data class RegisterRequest(
    val password: String,
    val username: String,
    val email: String,
    val age: Int,
    val gender: String,
    val location: String,
    val income: String, // Keeping as String if it's in a currency format
    val avatar: String
)
