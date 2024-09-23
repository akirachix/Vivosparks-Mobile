package com.akirachix.investikatrial.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        binding.loginbtn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = binding.usernameTextInput.editText?.text.toString().trim()
        val email = binding.emailTextInput.editText?.text.toString().trim()
        val password = binding.passwordTextInput.editText?.text.toString().trim()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val registerRequest = RegisterRequest(username, email, password)
        val apiInterface = ApiClient.retrofitInstance.create(SigninInterface::class.java)

        apiInterface.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, createprofileactivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "Failed response: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("RegisterActivity", "Error occurred: ${t.message}")
            }
        })
    }
}
