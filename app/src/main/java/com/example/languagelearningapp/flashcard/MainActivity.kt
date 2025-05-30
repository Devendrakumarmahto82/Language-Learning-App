package com.example.languagelearningapp.flashcard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.languagelearningapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {



    private lateinit var btnAddCard: Button
    private lateinit var btnStartQuiz: Button
    private lateinit var tvStats: TextView
    private lateinit var tvCardCount: TextView
    private lateinit var tvMasteredCount: TextView
    private lateinit var tvQuizStats: TextView
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        // Initialize views
        btnAddCard = findViewById(R.id.btnAddCard)
        btnStartQuiz = findViewById(R.id.btnStartQuiz)
        tvStats = findViewById(R.id.tvStats)
        tvCardCount = findViewById(R.id.tvCardCount)
        tvMasteredCount = findViewById(R.id.tvMasteredCount)
        tvQuizStats = findViewById(R.id.tvQuizStats)

        // Setup SharedPreferences
        sharedPreferences = getSharedPreferences("FlashcardPrefs", Context.MODE_PRIVATE)

        // Set up click listeners
        btnAddCard.setOnClickListener {
            startActivity(Intent(this, AddFlashcardActivity::class.java))
        }

        btnStartQuiz.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
    }
    override fun onResume() {
        super.onResume()
        updateStats()
    }

    private fun updateStats() {
        val flashcards = loadFlashcards()
        val masteredCount = flashcards.count { it.isMastered }
        val totalCards = flashcards.size

        // Update UI
        tvCardCount.text = "Total Cards: $totalCards"
        tvMasteredCount.text = "Mastered Cards: $masteredCount"

        if (totalCards == 0) {
            tvStats.text = "No flashcards created yet"
            btnStartQuiz.isEnabled = false
            tvQuizStats.text = "Quiz Score: N/A"
        } else {
            tvStats.text = "You have $totalCards flashcards"
            btnStartQuiz.isEnabled = true

            // Load and show quiz stats
            val correctAnswers = sharedPreferences.getInt("correctAnswers", 0)
            val totalAttempts = sharedPreferences.getInt("totalAttempts", 0)
            val score = if (totalAttempts > 0) (correctAnswers * 100 / totalAttempts) else 0
            tvQuizStats.text = "Quiz Score: $score% ($correctAnswers/$totalAttempts)"
        }
    }
    private fun loadFlashcards(): List<Flashcard> {
        val json = sharedPreferences.getString("flashcard_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<Flashcard>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    }

