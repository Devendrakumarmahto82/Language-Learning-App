package com.example.languagelearningapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FillBlankDao {
    @Query("SELECT * FROM fill_blank_table")
    suspend fun getAll(): List<FillBlankEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<FillBlankEntity>)
}
