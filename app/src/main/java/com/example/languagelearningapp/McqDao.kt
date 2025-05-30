package com.example.languagelearningapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface McqDao {
    @Query("SELECT * FROM mcq_table")
    suspend fun getAllMcqs(): List<McqEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mcqs: List<McqEntity>)
}
