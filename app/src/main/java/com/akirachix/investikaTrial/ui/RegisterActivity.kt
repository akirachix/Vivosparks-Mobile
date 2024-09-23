package com.akirachix.investikatrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.api.SigninInterface
import com.akirachix.investikaTrial.models.RegisterRequest
import com.akirachix.investikaTrial.models.RegisterResponse
import com.akirachix.investikaTrial.ui.createprofileactivity
import com.akirachix.investikatrial.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the register button click listener
        binding.loginbtn.setOnClickListener {
            registerUser() // Trigger the registration process when the button is clicked
        }
    }

    // Function to register the user using the API
    private fun registerUser() {
        val username = binding.usernameTextInput.editText?.text.toString().trim()
        val email = binding.emailTextInput.editText?.text.toString().trim()
        val password = binding.passwordTextInput.editText?.text.toString().trim()

        // Additional fields for the registration request
        val age = 27
        val gender = "female" // You might want to gather this from user input
        val location = "Nairobi" // Same for location
        val income = "5000.00" // Replace this with actual data
        val avatar = "ShadowClaw" // Use an actual avatar field from user input if available

        // Input validation to ensure non-empty fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the request object
        val registerRequest = RegisterRequest(
            username = username,
            email = email,
            password = password,
            age = age,
            gender = gender,
            location = location,
            income = income,
            avatar = avatar
        )

        // Make the API call using Retrofit
        val apiInterface = ApiClient.retrofitInstance.create(SigninInterface::class.java)
        apiInterface.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    // If registration is successful, navigate to the profile creation activity
                    Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@RegisterActivity, createprofileactivity::class.java).apply {
                        putExtra("username", username)
                        putExtra("email", email)
                        putExtra("password", password)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    // Handle unsuccessful registration responses
                    Toast.makeText(this@RegisterActivity, "Registration failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                // Handle failure due to network issues or other errors
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
