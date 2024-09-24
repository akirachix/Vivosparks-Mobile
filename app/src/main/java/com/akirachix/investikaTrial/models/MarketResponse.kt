package com.akirachix.investikaTrial.models

data class MarketResponse(
    val status: String,
    val from: String,
    val symbol: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double,
    val afterHours: Double,
    val preMarket: Double
)
