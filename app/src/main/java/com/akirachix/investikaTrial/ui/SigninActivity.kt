package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akirachix.investikaTrial.viewmodel.SignInViewModel
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivitySigninBinding
import com.akirachix.investikatrial.ui.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        // Configure Google Sign In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Observe login result
        signInViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess {
                navigateToMain()
            }.onFailure { throwable ->
                showError(throwable.localizedMessage ?: "Login failed.")
            }
        })

        // Observe Google sign-in result
        signInViewModel.googleSignInResult.observe(this, Observer { result ->
            result.onSuccess {
                navigateToMain()
            }.onFailure { throwable ->
                showError(throwable.localizedMessage ?: "Google Sign-In failed.")
            }
        })

        // Button click listeners
        binding.googleSignInButton.setOnClickListener { signInWithGoogle() }
        binding.loginbtn.setOnClickListener { handleEmailLogin() }
        binding.signUpText.setOnClickListener { navigateToSignUp() }
    }

    // Handle email login
    private fun handleEmailLogin() {
        val username = binding.usernameInput.text.toString()
        val password = binding.passwordInput.text.toString()

        if (signInViewModel.validateForm(username, password)) {
            signInViewModel.login(username, password)
        } else {
            showToast("Please enter both username and password")
        }
    }

    // Google sign-in
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Handle Google sign-in result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(data)
        }
    }

    // Google sign-in process
    private fun handleGoogleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            signInViewModel.firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
            showToast("Google Sign In failed")
        }
    }

    // Navigate to the main activity
    private fun navigateToMain() {
        startActivity(Intent(this, launchgameActivity::class.java))
        finish()
    }

    // Navigate to the sign-up screen
    private fun navigateToSignUp() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    // Show error message
    private fun showError(message: String) {
        Log.w(TAG, message)
        showToast(message)
    }

    // Show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "SigninActivity"
        private const val RC_SIGN_IN = 9001
    }
}
