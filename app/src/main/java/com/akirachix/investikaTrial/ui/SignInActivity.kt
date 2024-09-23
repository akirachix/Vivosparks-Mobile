package com.akirachix.investikatrial

import SignInViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.activity.viewModels
import com.akirachix.investikaTrial.ui.AwardActivity
import com.akirachix.investikaTrial.ui.LaunchGameActivity
import com.akirachix.investikatrial.databinding.ActivitySigninBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signUpText.setOnClickListener {
            var intent = Intent(this ,LaunchGameActivity::class.java)
            startActivity(intent)
            binding.loginbtn.setOnClickListener {
                var intent = Intent(this , AwardActivity::class.java)
            }
        }

    }


}
