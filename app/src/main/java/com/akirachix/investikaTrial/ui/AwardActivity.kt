package com.akirachix.investikaTrial.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
import kotlin.concurrent.fixedRateTimer

class AwardActivity : AppCompatActivity() {
    private lateinit var coinContainer: FrameLayout
    private lateinit var btnGetStarted: Button
    private var coinsToFall = 15
    private var timer: java.util.Timer? = null
    private var mediaPlayer: MediaPlayer? = null // MediaPlayer instance for sound

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_award)

        coinContainer = findViewById(R.id.coin_container)
        btnGetStarted = findViewById(R.id.btnGetStarted)

        // Start the coin animation
        startCoinAnimation()

        btnGetStarted.setOnClickListener {
            startActivity(Intent(this, DragonActivity::class.java))
        }
    }

    private fun startCoinAnimation() {
        val duration = 3000L // Total duration for the animation
        val interval = duration / coinsToFall // Interval between each coin drop

        timer = fixedRateTimer("coinTimer", initialDelay = 0, period = interval) {
            if (coinsToFall <= 0) {
                cancel() // Properly cancel the timer
                return@fixedRateTimer
            }

            runOnUiThread {
                Log.d("CoinAnimation", "Animating coin drop")
                showCoinAnimation(1) // Show one coin at a time
                coinsToFall-- // Decrease the count of coins to fall
            }
        }
    }

    private fun showCoinAnimation(coinsToAward: Int) {
        for (i in 1..coinsToAward) {
            val coinImageView = ImageView(this).apply {
                setImageResource(R.drawable.coin) // Ensure this resource exists
                layoutParams = FrameLayout.LayoutParams(dpToPx(50), dpToPx(50))
            }

            coinContainer.addView(coinImageView)

            // Set a random horizontal position for the coin
            val startX = (Math.random() * (coinContainer.width - coinImageView.width)).toFloat()
            coinImageView.translationX = startX
            coinImageView.translationY = -coinImageView.height.toFloat() // Start above the container

            // Set up the falling animation with increased speed
            val fallAnimation = ObjectAnimator.ofFloat(
                coinImageView,
                "translationY",
                -coinImageView.height.toFloat(),
                coinContainer.height.toFloat()
            ).apply {
                duration = 1000 // Set a shorter duration for a faster fall (adjusted to 1000ms)
                start()
            }

            fallAnimation.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    coinContainer.removeView(coinImageView) // Remove coin when the animation ends
                }
            })

            // Optional horizontal movement
            ObjectAnimator.ofFloat(
                coinImageView,
                "translationX",
                startX,
                startX + (Math.random() * 50 - 25).toFloat()
            ).apply {
                duration = fallAnimation.duration // Match duration
                start()
            }

            // Play the coin drop sound
            playCoinDropSound() // Call the sound method when the coin drops
            Log.d("CoinAnimation", "Coin falling from X: $startX")
        }
    }

    private fun playCoinDropSound() {
        // Initialize MediaPlayer with the coin drop sound
        mediaPlayer = MediaPlayer.create(this, R.raw.coin_drop) // Make sure to have the sound file in res/raw
        mediaPlayer?.start() // Start playing the sound

        mediaPlayer?.setOnCompletionListener {
            it.release() // Release the MediaPlayer once done
            mediaPlayer = null // Clear the reference
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel() // Cancel timer when activity is destroyed
        mediaPlayer?.release() // Release MediaPlayer if it exists
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
