package com.example.investikatrial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.investikatrial.databinding.ActivitySigninBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set up Google sign-in button
        binding.signInButton.setOnClickListener {
            signInWithGoogle()
        }

        // Set up regular login button
        binding.loginbtn.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                signInWithEmail(username, password)
            } else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up sign-up text
        binding.signUpText.setOnClickListener {
            // Navigate to sign up screen
            Toast.makeText(this, "Navigate to Sign Up screen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signInWithEmail(email: String, password: String) {
        binding.loginbtn.isEnabled = false // Disable button to prevent multiple clicks
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.loginbtn.isEnabled = true // Re-enable button after process
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Prevent going back to sign-in screen
                    Toast.makeText(this, "Authentication successful.", Toast.LENGTH_SHORT).show()
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Authentication failed."
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Google Sign In failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Prevent going back to sign-in screen
                    Toast.makeText(this, "Google Sign In successful", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val TAG = "SigninActivity"
        private const val RC_SIGN_IN = 9001
    }
}
