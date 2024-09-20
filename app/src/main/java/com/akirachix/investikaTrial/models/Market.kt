package com.akirachix.investikaTrial.models



data class Market(
    val id: Int,
    val name: String,
    val description: String,
    val riskLevel: String,
    val isActive: Boolean
)

