package com.akirachix.investikatrial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akirachix.investikaTrial.ui.HomeActivity
import com.akirachix.investikaTrial.ui.RegisterActivity
import com.akirachix.investikaTrial.viewmodel.SignInViewModel
import com.akirachix.investikatrial.databinding.ActivitySigninBinding
import com.akirachix.investikatrial.ui.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val signInViewModel: SignInViewModel by viewModels() // Add the ViewModel type here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        setupGoogleSignIn()
        observeLoginResult()
        setupClickListeners()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Your web client ID from Firebase
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun observeLoginResult() {
        // Observe email/password login result
        signInViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess { message ->
                navigateToMain()
                showToast(message) // Optional: Show success message
            }.onFailure { throwable ->
                showError(throwable.localizedMessage ?: "Login failed.")
            }
        })

        // Observe Google Sign-In result
        signInViewModel.googleSignInResult.observe(this, Observer { result ->
            result.onSuccess { message ->
                navigateToMain()
                showToast(message) // Optional: Show success message
            }.onFailure { throwable ->
                showError(throwable.localizedMessage ?: "Google Sign-In failed.")
            }
        })
    }

    private fun setupClickListeners() {
        binding.apply {
            googleSignInButton.setOnClickListener { signInWithGoogle() }
            loginbtn.setOnClickListener { handleEmailLogin() }
            signUpText.setOnClickListener { navigateToSignUp() }
        }
    }

    private fun handleEmailLogin() {
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (signInViewModel.validateForm(username, password)) {
            signInViewModel.login(username, password) // Triggers login via ViewModel
        } else {
            showToast("Please enter both username and password")
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(data)
        }
    }

    private fun handleGoogleSignInResult(data: Intent?) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
            account?.let {
                signInViewModel.firebaseAuthWithGoogle(it.toString()) // Pass the account to the ViewModel
            }
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
            showToast("Google Sign-In failed")
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun navigateToSignUp() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    private fun showError(message: String) {
        Log.w(TAG, message)
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "SigninActivity"
        private const val RC_SIGN_IN = 9001
    }
}
