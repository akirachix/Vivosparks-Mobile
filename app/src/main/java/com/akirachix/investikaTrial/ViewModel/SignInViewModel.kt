package com.akirachix.investikaTrial.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.api.SigninInterface
import com.akirachix.investikaTrial.models.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val _googleSignInResult = MutableLiveData<Result<String>>()
    val googleSignInResult: LiveData<Result<String>> = _googleSignInResult

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val apiService = ApiClient.retrofit.create(SigninInterface::class.java)

    // API Login Logic with Coroutines
    fun login(username: String, password: String) {
        if (!validateForm(username, password)) return

        val loginRequest = LoginRequest(username, password)

        viewModelScope.launch {
            try {
                val response = apiService.login(loginRequest) // Assuming login is a suspend function
                if (response.isSuccessful && response.body()?.status == "success") {
                    _loginResult.postValue(Result.success(response.body()?.message ?: "Login successful"))
                } else {
                    _loginResult.postValue(Result.failure(Throwable(response.body()?.message ?: "Login failed")))
                }
            } catch (e: HttpException) {
                _loginResult.postValue(Result.failure(Throwable("Network error: ${e.message()}")))
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(Throwable("An error occurred: ${e.localizedMessage}")))
            }
        }
    }

    // Google Sign-In Logic
    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _googleSignInResult.postValue(Result.success("Google Sign-In successful"))
            } else {
                _googleSignInResult.postValue(Result.failure(task.exception ?: Exception("Google Sign-In failed")))
            }
        }
    }

    // Validation Logic
    fun validateForm(username: String, password: String): Boolean {
        return when {
            username.isEmpty() -> {
                _loginResult.postValue(Result.failure(Throwable("Username is required")))
                false
            }
            password.isEmpty() -> {
                _loginResult.postValue(Result.failure(Throwable("Password is required")))
                false
            }
            password.length < 6 -> {
                _loginResult.postValue(Result.failure(Throwable("Password must be at least 6 characters long")))
                false
            }
            else -> true
        }
    }
}
