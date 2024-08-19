package com.example.recipeapp.network.api

import com.example.recipeapp.data.model.Categories
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.network.response.ApiResponse

class MealsRepository(
    private val remoteDataSource: IRemoteDataSource
): IMealsRepository {
    override suspend fun getAllMeals(): ApiResponse {
        return remoteDataSource.getAllMeals()
    }

    override suspend fun getCategories(): List<Categories> {
        return remoteDataSource.getCategories()
    }

    override suspend fun getMealById(mealId: Int): ApiResponse {
        return remoteDataSource.getMealById(mealId)
    }

    override suspend fun searchMealsByName(mealName: String): ApiResponse {
        return remoteDataSource.searchMealsByName(mealName)
    }

}