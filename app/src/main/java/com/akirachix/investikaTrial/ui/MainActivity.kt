package com.akirachix.investika.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.models.Question
import com.akirachix.investikaTrial.ui.MarketActivity
import com.akirachix.investikatrial.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questions: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup listeners for buttons
        setupListeners()

        // Fetch questions to display
        fetchQuestions()
    }

    private fun setupListeners() {
        // Button for option 1
        binding.option3Button.setOnClickListener { onAnswerSelected(0) }

        // Button for option 2 will navigate to MarketActivity
        binding.option2Button.setOnClickListener {
            // Navigate to MarketActivity
            val intent = Intent(this, MarketActivity::class.java)
            startActivity(intent)
        }

        // Button for option 3
        binding.option3Button.setOnClickListener { onAnswerSelected(2) }

        // Profile icon click (if needed)
        binding.profileIcon.setOnClickListener {
            // Do something when the profile icon is clicked
        }
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
        // Logic to display the question based on the index
        // e.g., binding.questionTextView.text = questions[index].text
    }

    private fun onAnswerSelected(answer: Int) {
        // Logic to handle answer selection
        // e.g., check if answer is correct, update score, etc.
    }
}
