package com.example.languagelearningapp

data class McqQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
