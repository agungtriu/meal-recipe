package com.agungtriu.themeal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Query("SELECT * FROM meals")
    fun selectMeals(): Flow<List<MealEntity>?>


    @Query("SELECT * FROM meals WHERE idMeal=:id")
    fun selectMeal(id: String): Flow<MealEntity?>

    @Query("DELETE FROM meals WHERE idMeal=:id")
    suspend fun deleteMeal(id: String)
}