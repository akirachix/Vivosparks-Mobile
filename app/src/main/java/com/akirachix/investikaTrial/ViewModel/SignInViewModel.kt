package com.akirachix.investikaTrial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.api.SigninInterface
import com.akirachix.investikaTrial.models.LoginRequest
import com.akirachix.investikaTrial.models.LoginResponse
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Login function
    fun login(username: String, password: String) {
        // Create a LoginRequest object
        val loginRequest = LoginRequest(username, password)

        // Use FirebaseAuth to sign in with username and password
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login success, return success message
                    _loginResult.value = Result.success("Login successful")
                } else {
                    // Login failed, return failure message
                    _loginResult.value = Result.failure(task.exception ?: Exception("Login failed"))
                }
            }
    }

    // Validate the input form
    fun validateForm(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}
