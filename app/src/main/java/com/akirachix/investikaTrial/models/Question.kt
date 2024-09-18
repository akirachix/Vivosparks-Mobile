package com.akirachix.investikaTrial.models

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val image: String

)
