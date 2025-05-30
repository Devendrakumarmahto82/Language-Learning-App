package com.example.languagelearningapp.flashcard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.languagelearningapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddFlashcardActivity : AppCompatActivity() {
    private lateinit var etQuestion: EditText
    private lateinit var etAnswer: EditText
    private lateinit var btnSave: Button
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_flashcard)
        // Initialize views
        etQuestion = findViewById(R.id.etQuestion)
        etAnswer = findViewById(R.id.etAnswer)
        btnSave = findViewById(R.id.btnSave)

        // Setup SharedPreferences
        sharedPreferences = getSharedPreferences("FlashcardPrefs", Context.MODE_PRIVATE)

        // Set up click listener
        btnSave.setOnClickListener {
            saveFlashcard()
        }
    }
    private fun saveFlashcard() {
        val question = etQuestion.text.toString().trim()
        val answer = etAnswer.text.toString().trim()

        if (question.isEmpty() || answer.isEmpty()) {
            Toast.makeText(this, "Please enter both question and answer", Toast.LENGTH_SHORT).show()
            return
        }

        // Load existing flashcards
        val flashcards = loadFlashcards().toMutableList()

        // Add new flashcard
        flashcards.add(Flashcard(question = question, answer = answer))

        // Save updated list
        saveFlashcards(flashcards)

        Toast.makeText(this, "Flashcard added!", Toast.LENGTH_SHORT).show()
        finish()
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

    private fun saveFlashcards(flashcards: List<Flashcard>) {
        val json = Gson().toJson(flashcards)
        sharedPreferences.edit().putString("flashcard_list", json).apply()
    }
    }
