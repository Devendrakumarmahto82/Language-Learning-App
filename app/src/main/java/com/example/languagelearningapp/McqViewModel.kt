package com.example.languagelearningapp

import androidx.lifecycle.ViewModel

class McqViewModel : ViewModel() {

    private val _questions = listOf(
        McqQuestion("What is the capital of France?", listOf("Paris", "London", "Rome", "Berlin"), 0),
        McqQuestion("Which is a programming language?", listOf("Python", "Snake", "Elephant", "Zebra"), 0),
        McqQuestion("Which one is a fruit?", listOf("Carrot", "Potato", "Banana", "Cucumber"), 2)
    )

    private var currentIndex = 0

    fun getCurrentQuestion(): McqQuestion? {
        return if (currentIndex < _questions.size) _questions[currentIndex] else null
    }

    fun checkAnswer(selectedIndex: Int): Boolean {
        return _questions[currentIndex].correctAnswerIndex == selectedIndex
    }

    fun hasNext(): Boolean = currentIndex < _questions.size - 1

    fun goToNext() {
        if (hasNext()) currentIndex++
    }
}
