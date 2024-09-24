package com.akirachix.investikatrial

import SignInViewModel
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.akirachix.investikaTrial.ui.AwardActivity
import com.akirachix.investikaTrial.ui.LaunchGameActivity
import com.akirachix.investikatrial.databinding.ActivitySigninBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var welcomeMediaPlayer: MediaPlayer // MediaPlayer for welcome sound

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize and start welcome sound if it hasn't already been started from splash
        if (!::welcomeMediaPlayer.isInitialized) {
            welcomeMediaPlayer = MediaPlayer.create(this, R.raw.game) // Replace with your actual sound file
            welcomeMediaPlayer.isLooping = true // Continue playing sound in a loop
            welcomeMediaPlayer.start() // Start playing sound
        }

        // Set up the sign-up and login buttons
        binding.signUpText.setOnClickListener {
            val intent = Intent(this, LaunchGameActivity::class.java)
            startActivity(intent)
        }

        binding.loginbtn.setOnClickListener {
            val intent = Intent(this, AwardActivity::class.java)
            startActivity(intent)
            // Optionally stop sound if necessary when navigating away
            stopWelcomeSound()
        }
    }

    // Stop the welcome sound when no longer needed
    private fun stopWelcomeSound() {
        if (::welcomeMediaPlayer.isInitialized && welcomeMediaPlayer.isPlaying) {
            welcomeMediaPlayer.stop()
            welcomeMediaPlayer.release() // Release resources used by MediaPlayer
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopWelcomeSound() // Ensure sound stops when activity is destroyed
    }
}






