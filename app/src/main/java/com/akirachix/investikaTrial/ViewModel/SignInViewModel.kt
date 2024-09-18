// SignInViewModel.kt
package com.akirachix.investikaTrial.ViewModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akirachix.investikatrial.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class SignInViewModel : ViewModel() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val _signInResult = MutableLiveData<Pair<Boolean, String?>>()
    val signInResult: LiveData<Pair<Boolean, String?>> = _signInResult

    private val _signOutResult = MutableLiveData<Boolean>()
    val signOutResult: LiveData<Boolean> = _signOutResult

    private val RC_SIGN_IN = 9001

    // Initialize Google Sign-In options
    fun initializeGoogleSignIn(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.cdSKtUvnl9Q4Y3NtvSWWDYmLPctEdlfQ)) // Use correct web client ID
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    // Get the Google Sign-In intent
    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    // Handle Google Sign-In result and update LiveData
    fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            val email = account?.email
            _signInResult.value = Pair(true, email)
        } catch (e: ApiException) {
            _signInResult.value = Pair(false, null)
        }
    }

    // Sign out from Google and update LiveData
    fun signOut(context: Context) {
        googleSignInClient.signOut()
            .addOnCompleteListener(context as Activity) {
                _signOutResult.value = true
            }
    }
}
