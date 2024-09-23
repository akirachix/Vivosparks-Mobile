import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.api.SigninInterface
import com.akirachix.investikaTrial.models.LoginRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import com.co.api.ApiClient // Adjust the package name accordingly
import com.yourpackage.api.ApiInterface // Adjust the package name accordingly
import com.yourpackage.models.LoginRequest // Adjust the package name accordingly


class SignInViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val apiInterface: SigninInterface = ApiClient.api

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val _googleSignInResult = MutableLiveData<Result<String>>()
    val googleSignInResult: LiveData<Result<String>> = _googleSignInResult

    fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)

        viewModelScope.launch {
            try {
                val loginResponse = apiInterface.login(loginRequest)
                // Handle successful login response
                _loginResult.value = Result.success(loginResponse.message)
            } catch (e: Exception) {
                // Handle error response
                _loginResult.value = Result.failure(e)
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

