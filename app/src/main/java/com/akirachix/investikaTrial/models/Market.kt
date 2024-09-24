package com.akirachix.investikaTrial.models

data class Market(
    val t: Long,   // Timestamp
    val v: Double, // Volume
    val o: Double, // Open price
    val c: Double, // Close price
    val h: Double, // High price
    val l: Double, // Low price
)