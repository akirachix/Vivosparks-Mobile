package com.akirachix.investikatrial

import SignInViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.activity.viewModels
import com.akirachix.investikaTrial.ui.awardActivity
import com.akirachix.investikaTrial.ui.launchgameActivity
import com.akirachix.investikatrial.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding


    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signUpText.setOnClickListener {
            var intent = Intent(this ,launchgameActivity::class.java)
            startActivity(intent)
            binding.loginbtn.setOnClickListener {
                var intent = Intent(this , awardActivity::class.java)
            }
        }

    }


}
