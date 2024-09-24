package com.akirachix.investikaTrial.ui

import SigninActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handler to delay for 3 seconds
        Handler().postDelayed({
            // Navigate to LoginActivity after 3 seconds
            val intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
            // Finish SplashScreenActivity so it can't be returned to
            finish()
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}