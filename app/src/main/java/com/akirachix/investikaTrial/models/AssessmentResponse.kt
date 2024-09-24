package com.akirachix.investikaTrial.models

data class AssessmentResponse(
    val assessment_id: Int,
    val question_text: String,
    val question_image: String,
    val answers: List<Answer>,
    val is_active: Boolean,
    val taken_at: String,
    val user_id: Int?

)

data class Answer(
    val text: String,
    val option: String
)


