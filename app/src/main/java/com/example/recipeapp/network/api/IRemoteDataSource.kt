package com.example.recipeapp.network.api

import com.example.recipeapp.data.model.Categories
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.network.response.ApiResponse

interface IRemoteDataSource {
    suspend fun getAllMeals(): ApiResponse

    suspend fun getCategories(): List<Categories>

    suspend fun getMealById(mealId: Int): ApiResponse

    suspend fun searchMealsByName(mealName: String): ApiResponse

    suspend fun getRandomMeal(): List<Meal>

}