package com.akirachix.investika.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akirachix.investikaTrial.api.ApiClient
import com.akirachix.investikaTrial.models.Question
import com.akirachix.investikaTrial.models.QuestionResponse
import com.akirachix.investikaTrial.ui.CorporateActivity
import com.akirachix.investikatrial.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questions: List<Question>  // Initialize as late

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        fetchQuestions()  // Fetch questions when the activity is created
    }

    private fun setupListeners() {
        binding.option1Button.setOnClickListener {
            val intent = Intent(this, CorporateActivity::class.java)
            startActivity(intent)
        }

        // Assign correct listeners to the buttons
        binding.option2Button.setOnClickListener { onAnswerSelected(0) }
        binding.option3Button.setOnClickListener { onAnswerSelected(1) }
        binding.option3Button.setOnClickListener { onAnswerSelected(3) }
        binding.option3Button.setOnClickListener { onAnswerSelected(4) }



    }

    private fun fetchQuestions() {
        lifecycleScope.launch {
            try {
                val questionResponse: QuestionResponse = ApiClient.api.getQuestions()
                // Assign questions from response
                questions = questionResponse.questions

                // Display the first question if there are any
                if (questions.isNotEmpty()) {
                    displayQuestion(currentQuestionIndex)
                } else {
                    Toast.makeText(this@MainActivity, "No questions available", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching questions", e)
                Toast.makeText(this@MainActivity, "Failed to fetch questions: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayQuestion(index: Int) {
        if (questions.isNotEmpty()) {
            val question = questions[index]
            // Update UI with the current question and its options
            binding.questionText.text = question.question
            binding.option1Button.text = question.options[0]
            binding.option2Button.text = question.options[1]
            binding.option3Button.text = question.options[2]
        }
    }

    private fun onAnswerSelected(answer: Int) {
        // Handle answer selection (e.g., check if correct and update score)
        val correctAnswer = questions[currentQuestionIndex].correctAnswer
        if (answer == correctAnswer) {
            score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show()
        }

        // Move to next question
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            displayQuestion(currentQuestionIndex)
        } else {
            // End of quiz
            Toast.makeText(this, "Quiz finished! Score: $score", Toast.LENGTH_SHORT).show()
        }
    }
}
