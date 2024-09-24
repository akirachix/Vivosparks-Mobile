package com.akirachix.investikaTrial.models

data class QuestionResponse(
    val questions: List<Question>
) {
    private var callback: ((QuestionResponse) -> Unit)? = null

    fun setCallback(callback: (QuestionResponse) -> Unit) {
        this.callback = callback
    }

    fun enqueue() {
        callback?.invoke(this)
    }
}


