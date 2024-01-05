package com.agungtriu.themeal.data

import com.agungtriu.themeal.data.local.MealEntity
import com.agungtriu.themeal.data.remote.response.ResponseAreas
import com.agungtriu.themeal.data.remote.response.ResponseCategories
import com.agungtriu.themeal.data.remote.response.ResponseDetail
import com.agungtriu.themeal.data.remote.response.ResponseFilter
import com.agungtriu.themeal.data.remote.response.ResponseSearch
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertMeal(mealEntity: MealEntity)
    fun selectMeals(): Flow<List<MealEntity>?>
    fun selectMeal(id: String): Flow<MealEntity?>
    suspend fun deleteMeal(id: String)
    suspend fun getSearch(key: String?): ResponseSearch
    suspend fun getFilter(category: String?, area: String?): ResponseFilter
    suspend fun getCategories(): ResponseCategories
    suspend fun getAreas(): ResponseAreas
    suspend fun getDetail(id: String): ResponseDetail
}