package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.SignInActivity
import com.akirachix.investikatrial.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the MediaPlayer with the audio file in res/raw
        mediaPlayer = MediaPlayer.create(this, R.raw.game)

        // Play the sound
        mediaPlayer.start()

        // Handler to delay for 3 seconds
        Handler().postDelayed({
            // Stop and release the MediaPlayer when done
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()

            // Navigate to SignInActivity after 3 seconds
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)

            // Finish SplashScreenActivity so it can't be returned to
            finish()
        }, 5000) // 5000 milliseconds = 3 seconds
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer if the activity is destroyed
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}






