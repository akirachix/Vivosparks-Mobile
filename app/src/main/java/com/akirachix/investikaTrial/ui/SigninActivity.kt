import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivitySigninBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    // ViewModel for the activity
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Google Sign-In configuration
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Make sure the ID is in strings.xml
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set up listeners for UI interactions
        setupListeners()

        // Observe LiveData from ViewModel
        observeViewModel()
    }

    private fun setupListeners() {
        binding.loginbtn.setOnClickListener {
            val username = binding.usernameInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            if (viewModel.validateForm(username, password)) {
                viewModel.login(username, password)
            }
        }

        binding.signUpText.setOnClickListener {
            val intent = Intent(this,this::class.java)
            startActivity(intent)
        }

        binding.googleSignInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInResultLauncher.launch(signInIntent)
        }
    }

    private val googleSignInResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        task.getResult(Exception::class.java)?.let { account ->
            viewModel.firebaseAuthWithGoogle(account)
        } ?: run {
            Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(this, Observer { result ->
            result.fold(
                onSuccess = { showToast(it) },
                onFailure = { showToast(it.message ?: "Login failed") }
            )
        })

        viewModel.googleSignInResult.observe(this, Observer { result ->
            result.fold(
                onSuccess = { showToast(it) },
                onFailure = { showToast(it.message ?: "Google Sign-In failed") }
            )
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
