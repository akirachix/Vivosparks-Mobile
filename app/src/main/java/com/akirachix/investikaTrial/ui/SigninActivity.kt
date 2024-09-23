package com.akirachix.investikaTrial.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import com.akirachix.investikatrial.databinding.ActivitySigninBinding
import com.akirachix.investikaTrial.viewmodel.SignInViewModel
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.ui.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

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

        setupGoogleSignIn()
        observeLoginResults()
        setupClickListeners()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun observeLoginResults() {
        signInViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess { message ->
                navigateToMain()
                showToast(message)
            }.onFailure { throwable ->
                showError(throwable.localizedMessage ?: "Login failed")
            }
        })

        signInViewModel.googleSignInResult.observe(this, Observer { result ->
            result.onSuccess { message ->
                navigateToMain()
                showToast(message)
            }.onFailure { throwable ->
                showError(throwable.localizedMessage ?: "Google Sign-In failed")
            }
        })
    }

    private fun setupClickListeners() {
        binding.apply {
//            googleSignInButton.setOnClickListener { signInWithGoogle() }
            loginbtn.setOnClickListener { handleEmailLogin() }
            signUpText.setOnClickListener { navigateToSignUp() }
        }
    }

    private fun handleEmailLogin() {
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (signInViewModel.validateForm(username, password)) {
            signInViewModel.login(username, password)
        } else {
            showToast("Please enter both username and password")
        }
    }

//    private fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK && result.data != null) {
//                val account = result.data?.getParcelableExtra<GoogleSignInAccount>("GOOGLE_ACCOUNT")
//                account?.idToken?.let { firebaseAuthWithGoogle(it) }
//            }
//        }
//        activityResultLauncher.launch(signInIntent)
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                signInViewModel.googleSignInResult.value = "Successfully signed in with Google"
//            } else {
//                signInViewModel.googleSignInResult.value = "Failed to sign in with Google"
//            }
//        }
//    }

    private fun navigateToMain() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun navigateToSignUp() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    private fun showError(message: String) {
        Log.e(TAG, message)
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "SigninActivity"
    }
}
