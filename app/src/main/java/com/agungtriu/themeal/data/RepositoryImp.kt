package com.agungtriu.themeal.data

import com.agungtriu.themeal.data.local.AppDatabase
import com.agungtriu.themeal.data.local.MealEntity
import com.agungtriu.themeal.data.remote.ApiService
import com.agungtriu.themeal.data.remote.response.ResponseAreas
import com.agungtriu.themeal.data.remote.response.ResponseCategories
import com.agungtriu.themeal.data.remote.response.ResponseDetail
import com.agungtriu.themeal.data.remote.response.ResponseFilter
import com.agungtriu.themeal.data.remote.response.ResponseSearch
import javax.inject.Inject


class RepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : Repository {
    override suspend fun insertMeal(mealEntity: MealEntity) {
        appDatabase.mealDao().insertMeal(mealEntity)
    }

    override fun selectMeals() = appDatabase.mealDao().selectMeals()

    override fun selectMeal(id: String) = appDatabase.mealDao().selectMeal(id)

    override suspend fun deleteMeal(id: String) {
        appDatabase.mealDao().deleteMeal(id)
    }

    override suspend fun getSearch(key: String?): ResponseSearch = apiService.getSearch(key)

    override suspend fun getFilter(category: String?, area: String?): ResponseFilter =
        apiService.getFilter(category, area)

    override suspend fun getCategories(): ResponseCategories = apiService.getCategories()

    override suspend fun getAreas(): ResponseAreas = apiService.getAreas()

    override suspend fun getDetail(id: String): ResponseDetail = apiService.getDetail(id)
}