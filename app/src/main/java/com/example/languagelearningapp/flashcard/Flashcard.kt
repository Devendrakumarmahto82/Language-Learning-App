package com.example.languagelearningapp.flashcard

import java.util.UUID

data class Flashcard(
    val id: String = UUID.randomUUID().toString(),
    val question: String,
    val answer: String,
    var isMastered: Boolean = false // Make sure this property exists
)