package com.example.languagelearningapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mcq_table")
data class McqEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctIndex: Int
)
