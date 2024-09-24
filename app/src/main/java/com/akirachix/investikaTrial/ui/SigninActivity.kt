package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akirachix.investikatrial.databinding.ActivitySigninBinding
import com.akirachix.investikaTrial.viewmodel.SignInViewModel
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide the action bar for cleaner UI
        supportActionBar?.hide()

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Observe the login results from ViewModel
        observeLoginResults()

        // Setup click listeners for login buttons
        setupClickListeners()
    }

    // Observe login results from the ViewModel
    private fun observeLoginResults() {
        // Email/password login result observer
        signInViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess { message ->
                showToast(message)
                // Navigate to the main activity or another screen after successful login
                navigateToMainActivity()
            }.onFailure { throwable ->
                showError(throwable.localizedMessage ?: "Login failed")
            }
        })
    }

    // Setup the click listeners for UI buttons
    private fun setupClickListeners() {
        binding.apply {
            // Handle the click event for email/password login
            loginbtn.setOnClickListener { handleEmailLogin() }

            // Navigate to the register activity when user clicks the sign-up link
            signUpText.setOnClickListener { navigateToSignUp() }
        }
    }

    // Handles email and password login
    private fun handleEmailLogin() {
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        // Validate form input
        if (signInViewModel.validateForm(username, password)) {
            // Call login function in ViewModel
            signInViewModel.login(username, password)
        } else {
            showToast("Please enter both username and password")
        }
    }

    // Navigate to the sign-up activity
    private fun navigateToSignUp() {
        // Assuming you have a SignUpActivity to navigate to
        val intent = Intent(this, this::class.java)
        startActivity(intent)
    }

    // Show error message
    private fun showError(message: String) {
        Log.e(TAG, message)
        showToast(message)
    }

    // Display toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Navigate to the main activity after successful login
    private fun navigateToMainActivity() {
        // Replace MainActivity::class.java with your actual main activity class
        val intent = Intent(this, this::class.java)
        startActivity(intent)
        finish() // Optional: Close this activity
    }

    companion object {
        private const val TAG = "SigninActivity"
    }
}
