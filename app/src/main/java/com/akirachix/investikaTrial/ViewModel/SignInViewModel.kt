package com.akirachix.investikaTrial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.api.SigninInterface
import com.akirachix.investikaTrial.models.LoginRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val apiInterface: SigninInterface = ApiClient.retrofitInstance.create(SigninInterface::class.java)

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val _googleSignInResult = MutableLiveData<Result<String>>()
    val googleSignInResult: LiveData<Result<String>> = _googleSignInResult

    fun login(username: String, password: String) {
        if (!validateForm(username, password)) {
            _loginResult.value = Result.failure(IllegalArgumentException("Username and password must not be empty"))
            return
        }

        val loginRequest = LoginRequest(username, password)
        viewModelScope.launch {
            try {
                val loginResponse = apiInterface.login(loginRequest) // Ensure this is correctly defined in your ApiInterface
                _loginResult.value = Result.success(loginResponse.message()) // Handle successful response
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e) // Handle error response
            }
        }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).await()
                _googleSignInResult.value = Result.success("Google Sign-In successful")
            } catch (e: Exception) {
                _googleSignInResult.value = Result.failure(e)
            }
        }
    }

    fun validateForm(username: String, password: String): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }
}
