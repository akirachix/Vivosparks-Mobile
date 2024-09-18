package com.akirachix.investika.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investika.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  ViewBinding
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginbtn.setOnClickListener {
            val intent = Intent(this, RegisterProfileActivity::class.java)
            startActivity(intent)
        }
    }
}

