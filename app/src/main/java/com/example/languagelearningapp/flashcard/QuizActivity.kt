package com.example.languagelearningapp.flashcard

import android.content.Context
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

class QuizActivity : AppCompatActivity() {
    private lateinit var tvQuestion: TextView
    private lateinit var tvProgress: TextView
    private lateinit var tvScore: TextView
    private lateinit var btnShowAnswer: Button
    private lateinit var btnNext: Button
    private lateinit var btnShuffle: Button
    private lateinit var btnGotRight: Button
    private lateinit var btnGotWrong: Button
    private lateinit var cardView: CardView
    private lateinit var sharedPreferences: SharedPreferences

    private var flashcards = mutableListOf<Flashcard>()
    private var currentIndex = 0
    private var showingAnswer = false
    private var correctAnswers = 0
    private var totalAttempts = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)


        // Initialize views
        initViews()

        // Setup SharedPreferences
        sharedPreferences = getSharedPreferences("FlashcardPrefs", Context.MODE_PRIVATE)
        loadProgress()

        // Load flashcards
        flashcards = loadFlashcards().toMutableList()

        // Check if there are flashcards
        if (flashcards.isEmpty()) {
            tvQuestion.text = "No flashcards available"
            disableButtons()
            return
        }

        // Setup initial state
        showQuestion()
        updateProgress()
        updateScore()

        // Set up click listeners
        setupClickListeners()

    }

    private fun initViews() {
        tvQuestion = findViewById(R.id.tvQuestion)
        tvProgress = findViewById(R.id.tvProgress)
        tvScore = findViewById(R.id.tvScore)
        btnShowAnswer = findViewById(R.id.btnShowAnswer)
        btnNext = findViewById(R.id.btnNext)
        btnShuffle = findViewById(R.id.btnShuffle)
        btnGotRight = findViewById(R.id.btnGotRight)
        btnGotWrong = findViewById(R.id.btnGotWrong)
        cardView = findViewById(R.id.cardView)
    }

    private fun disableButtons() {
        btnShowAnswer.isEnabled = false
        btnNext.isEnabled = false
        btnShuffle.isEnabled = false
        btnGotRight.isEnabled = false
        btnGotWrong.isEnabled = false
    }
    private fun setupClickListeners() {
        btnShowAnswer.setOnClickListener {
            if (showingAnswer) {
                showQuestion()
            } else {
                showAnswer()
            }
        }

        btnNext.setOnClickListener {
            moveToNextCard()
        }

        btnShuffle.setOnClickListener {
            shuffleCards()
        }

        btnGotRight.setOnClickListener {
            markCardAsMastered(true)
            moveToNextCard()
        }

        btnGotWrong.setOnClickListener {
            markCardAsMastered(false)
            moveToNextCard()
        }
        cardView.setOnClickListener {
            flipCard()
        }
    }
    private fun showQuestion() {
        tvQuestion.text = flashcards[currentIndex].question
        btnShowAnswer.text = "SHOW ANSWER"
        showingAnswer = false
    }

    private fun showAnswer() {
        tvQuestion.text = flashcards[currentIndex].answer
        btnShowAnswer.text = "SHOW QUESTION"
        showingAnswer = true
    }

    private fun moveToNextCard() {
        // Find next unmastered card
        var nextIndex = (currentIndex + 1) % flashcards.size
        var attempts = 0

        while (flashcards[nextIndex].isMastered && attempts < flashcards.size) {
            nextIndex = (nextIndex + 1) % flashcards.size
            attempts++
        }

        currentIndex = nextIndex
        showQuestion()
        updateProgress()
    }
    private fun shuffleCards() {
        flashcards.shuffle()
        currentIndex = 0
        showQuestion()
        updateProgress()
    }

    private fun markCardAsMastered(isCorrect: Boolean) {
        val updatedCard = flashcards[currentIndex].copy(isMastered = isCorrect)
        flashcards[currentIndex] = updatedCard

        totalAttempts++

        if (isCorrect) {
            correctAnswers++
        }

        updateScore()
        saveProgress()
        saveFlashcards()
    }

    private fun updateProgress() {
        val masteredCount = flashcards.count { it.isMastered }
        tvProgress.text = "${currentIndex + 1}/${flashcards.size} (${masteredCount} mastered)"
    }

    private fun updateScore() {
        val score = if (totalAttempts > 0) (correctAnswers * 100 / totalAttempts) else 0
        tvScore.text = "Score: $score% ($correctAnswers/$totalAttempts)"
    }
    private fun flipCard() {
        cardView.animate()
            .rotationY(90f)
            .setDuration(250)
            .withEndAction {
                if (showingAnswer) {
                    showQuestion()
                } else {
                    showAnswer()
                }

                cardView.rotationY = -90f
                cardView.animate()
                    .rotationY(0f)
                    .setDuration(250)
                    .start()
            }
            .start()
    }
    private fun saveProgress() {
        sharedPreferences.edit()
            .putInt("correctAnswers", correctAnswers)
            .putInt("totalAttempts", totalAttempts)
            .apply()
    }

    private fun saveFlashcards() {
        val json = Gson().toJson(flashcards)
        sharedPreferences.edit().putString("flashcard_list", json).apply()
    }

    private fun loadProgress() {
        correctAnswers = sharedPreferences.getInt("correctAnswers", 0)
        totalAttempts = sharedPreferences.getInt("totalAttempts", 0)
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

    override fun onPause() {
        super.onPause()
        saveProgress()
        saveFlashcards()
    }
}