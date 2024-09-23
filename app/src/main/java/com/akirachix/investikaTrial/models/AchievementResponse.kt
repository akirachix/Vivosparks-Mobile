package com.akirachix.investikaTrial.models

data class AchievementResponse(
    val achievements: List<Achievement>
) {
    private var callback: ((AchievementResponse) -> Unit)? = null

    fun setCallback(callback: (AchievementResponse) -> Unit) {
        this.callback = callback
    }

    fun enqueue() {
        callback?.invoke(this)
    }
}
