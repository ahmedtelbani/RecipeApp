package com.example.recipeapp.data.network.api

import com.example.recipeapp.data.model.Category
import com.example.recipeapp.data.network.response.ApiResponse

interface IRemoteDataSource {
    suspend fun getAllMeals(): ApiResponse

    suspend fun getCategories(): List<Category>

    suspend fun getMealById(mealId: Int): ApiResponse

    suspend fun searchMealsByName(mealName: String): ApiResponse

    suspend fun getRandomMeal(): ApiResponse

}