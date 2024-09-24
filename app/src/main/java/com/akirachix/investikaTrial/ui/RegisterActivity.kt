package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikatrial.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the register button click listener
        binding.loginbtn.setOnClickListener {
            val username = binding.usernameTextInput.editText?.text.toString().trim()
            val email = binding.emailTextInput.editText?.text.toString().trim()
            val password = binding.passwordTextInput.editText?.text.toString().trim()

            // Input validation
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Proceed to CreateProfileActivity with the collected data
                val intent = Intent(this@RegisterActivity, Createprofileactivity::class.java).apply {
                    putExtra("username", username)
                    putExtra("email", email)
                    putExtra("password", password)
                }
                startActivity(intent)
                finish()
            }
        }
    }
}
