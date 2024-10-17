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

        binding.loginbtn.setOnClickListener {
            val username = binding.idUserName.text.toString().trim() // Assuming you add this EditText for username
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmpassword.text.toString().trim()

            // Basic validation
            if (username.isEmpty()) {
                Toast.makeText(this, "Username is mandatory", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password is mandatory", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proceed to CreateProfileActivity with the collected data
            val intent = Intent(this, CreateProfileActivity::class.java).apply {
                putExtra("username", username)
                putExtra("email", email)
                putExtra("password", password)
            }
            startActivity(intent)
            finish()
        }
    }
}
