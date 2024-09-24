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
import com.akirachix.investikatrial.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding


    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginbtn.setOnClickListener {
            val intent = Intent(this, DragonActivity::class.java)
            startActivity(intent)


        }
        supportActionBar?.hide()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In options
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
        binding.loginbtn.setOnClickListener { handleEmailLogin() }
        binding.signUpText.setOnClickListener { navigateToSignUp() }
    }

    // Handle email login
    private fun handleEmailLogin() {
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

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
        startActivity(Intent(this, HighRiskPaths::class.java))
        finish()
    }

    // Navigate to the sign-up screen
    private fun navigateToSignUp() {
        startActivity(Intent(this, LaunchGameActivity::class.java))
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
        private const val TAG = "SignInActivity" // Consistent TAG name
        private const val RC_SIGN_IN = 9001
    }

}
