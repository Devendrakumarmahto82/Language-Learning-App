package com.example.languagelearningapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fill_blank_table")
data class FillBlankEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val answer: String
)
