package com.example.languagelearningapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [McqEntity::class], version = 2)
abstract class AppDatabase2 : RoomDatabase() {
    abstract fun mcqDao(): McqDao

    companion object {
        @Volatile private var instance: AppDatabase2? = null

        fun getDatabase(context: Context): AppDatabase2 {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase2::class.java, "mcq_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
