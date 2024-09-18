package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.api.SignInClient
import com.akirachix.investikaTrial.api.ApiInterface
import com.akirachix.investikaTrial.models.LoginRequest
import com.akirachix.investikaTrial.models.LoginResponse
import com.akirachix.investikatrial.databinding.ActivitySigninBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.loginbtn.setOnClickListener {
            if (validateForm()) {
                val username = binding.usernameInput.text.toString().trim()
                val password = binding.passwordInput.text.toString().trim()
                login(username, password)
            }
        }

        binding.signUpText.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (username.isEmpty()) {
            binding.usernameInput.error = "Username is required"
            isValid = false
        } else {
            binding.usernameInput.error = null
        }

        if (password.isEmpty()) {
            binding.passwordInput.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.passwordInput.error = "Password must be at least 6 characters long"
            isValid = false
        } else {
            binding.passwordInput.error = null
        }

        return isValid
    }

    private fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        val apiService = SignInClient.retrofitInstance.create(ApiInterface::class.java)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.status == "success") {
                        handleSuccessfulLogin(responseBody.message)
                    } else {
                        handleFailedLogin("Login failed: ${responseBody?.message ?: response.message()}")
                    }
                } else {
                    handleFailedLogin("Login failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                handleFailedLogin("Network error: ${t.message}")
            }
        })
    }

    private fun handleSuccessfulLogin(message: String?) {
        Toast.makeText(this, message ?: "Login successful", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleFailedLogin(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
