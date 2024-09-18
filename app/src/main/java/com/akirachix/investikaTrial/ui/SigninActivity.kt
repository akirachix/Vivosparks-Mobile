package com.akirachix.investikatrial.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast
import com.akirachix.investikaTrial.api.SignInClient
import com.akirachix.investikaTrial.api.ApiInterface
import com.akirachix.investikatrial.databinding.ActivitySigninBinding
import com.akirachix.investikaTrial.models.LoginRequest
import com.akirachix.investikaTrial.models.LoginResponse
import com.akirachix.investikaTrial.ui.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninActivity : AppCompatActivity() {

    // ViewBinding object to access the views in the layout
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding and set the content view to the root of the layout
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listeners for the login button and sign-up text
        setupListeners()
    }

    private fun setupListeners() {
        // Handle the login button click
        binding.loginbtn.setOnClickListener {
            if (validateForm()) {
                val username = binding.usernameInput.text.toString().trim()
                val password = binding.passwordInput.text.toString().trim()
                login(username, password)
            }
        }

        // Handle sign-up text click to navigate to SignupActivity
        binding.signUpText.setOnClickListener {
            val intent = Intent(this,HomeActivity ::class.java)
            startActivity(intent)
        }
    }

    // Validate the form inputs for username and password
    private fun validateForm(): Boolean {
        var isValid = true

        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        // Validate username field
        if (username.isEmpty()) {
            binding.usernameInput.error = "Username is required"
            isValid = false
        } else {
            binding.usernameInput.error = null
        }

        // Validate password field
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

    // Perform the login by making an API call with Retrofit
    private fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        val apiService = SignInClient.retrofitInstance.create(ApiInterface::class.java)


        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.status == "success") {
                    handleSuccessfulLogin(response.body()?.message)
                } else {
                    handleFailedLogin("Login failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                handleFailedLogin("Network error: ${t.message}")
            }
        })
    }

    // Handle a successful login
    private fun handleSuccessfulLogin(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        // Navigate to the next activity (e.g., HomeActivity)
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()  // Close the login activity so the user can't go back to it
    }

    // Handle a failed login by showing a toast message
    private fun handleFailedLogin(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
