package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.R
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
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.isLooping = true // Loop the sound
        mediaPlayer.start()

        // Navigate to SignInActivity after 5 seconds
        Handler().postDelayed({
            navigateToSignIn()
        }, 5000)

        // Set a listener to release the media player when done
        mediaPlayer.setOnCompletionListener {
            navigateToSignIn()
        }
    }

    private fun navigateToSignIn() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()

        // Navigate to SignInActivity
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer if the activity is destroyed
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
