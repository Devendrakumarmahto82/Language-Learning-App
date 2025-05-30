package com.example.languagelearningapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [McqEntity::class, FillBlankEntity::class], version = 2)
abstract class AppDatabase3 : RoomDatabase() {

    abstract fun mcqDao(): McqDao
    abstract fun fillBlankDao(): FillBlankDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase3? = null

        fun getDatabase(context: Context): AppDatabase3 {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase3::class.java,
                    "language_learning_db"
                )
                    .fallbackToDestructiveMigration() // Optional: handles version updates
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
