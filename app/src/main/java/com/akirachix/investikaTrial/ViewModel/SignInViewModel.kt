import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akirachix.investikaTrial.api.ApiInterface
import com.akirachix.investikaTrial.api.SignInClient
import com.akirachix.investikaTrial.models.LoginRequest
import com.akirachix.investikaTrial.models.LoginResponse
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val _googleSignInResult = MutableLiveData<Result<String>>()
    val googleSignInResult: LiveData<Result<String>> = _googleSignInResult

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // API Login Logic
    fun login(username: String, password: String) {
        if (!validateForm(username, password)) return

        val loginRequest = LoginRequest(username, password)
        val apiService = SignInClient.retrofitInstance.create(ApiInterface::class.java)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.status == "success") {
                    _loginResult.postValue(Result.success(response.body()?.message ?: "Login successful"))
                } else {
                    _loginResult.postValue(Result.failure(Throwable(response.body()?.message ?: "Login failed")))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.postValue(Result.failure(t))
            }
        })
    }

    // Google Sign-In Logic
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
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
