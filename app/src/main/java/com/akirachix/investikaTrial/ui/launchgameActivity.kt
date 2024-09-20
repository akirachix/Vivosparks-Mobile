package com.akirachix.investikaTrial.ui
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.investika.ui.MainActivity
import com.akirachix.investikatrial.R
import com.akirachix.investikatrial.databinding.ActivityLaunchgameBinding


class launchgameActivity : AppCompatActivity() {
    lateinit var binding: ActivityLaunchgameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launchgame)

        binding = ActivityLaunchgameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to MainActivity on button click using View Binding
        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
