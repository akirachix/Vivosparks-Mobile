package com.akirachix.investikaTrial.models

data class OpenCloseResponse(
    val symbol: String,
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val volume: Long,
    val afterHours: Double?,   // Nullable to handle null values
    val preMarket: Double?     // Nullable to handle null values
)
