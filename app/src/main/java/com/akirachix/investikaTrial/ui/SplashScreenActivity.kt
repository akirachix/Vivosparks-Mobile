package com.akirachix.investikaTrial.ui

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

     
        Handler().postDelayed({
        
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        
            finish()
        }, 3000)
    }
}