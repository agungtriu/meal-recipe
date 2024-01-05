package com.agungtriu.themeal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}