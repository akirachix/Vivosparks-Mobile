package com.akirachix.investika.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.models.Question
import com.akirachix.investikaTrial.ui.FollowupActivity
import com.akirachix.investikatrial.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ActivityMainBinding
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questions: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        fetchQuestions()
    }



    private fun setupListeners() {

        binding.option1Button.setOnClickListener{
            val intent = Intent(this,FollowupActivity::class.java )
            startActivity(intent)
        }


        binding.option2Button.setOnClickListener { onAnswerSelected(0) }
        binding.option3Button.setOnClickListener { onAnswerSelected(1) }
        binding.option3Button.setOnClickListener { onAnswerSelected(2) }
        binding.profileIcon.setOnClickListener {  }
    }

    private fun fetchQuestions() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.api.getQuestions()
                questions = response.questions
                displayQuestion(currentQuestionIndex)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Failed to fetch questions: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayQuestion(index: Int) {
    }

    private fun onAnswerSelected(answer: Int) {
    }
}
