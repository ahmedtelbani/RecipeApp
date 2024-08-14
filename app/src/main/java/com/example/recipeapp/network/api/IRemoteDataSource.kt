package com.example.recipeapp.network.api

interface IRemoteDataSource {
    suspend fun getAllMeals(): List<MealDTO>

    suspend fun getMealById(mealId: Int): MealDTO?

    suspend fun searchMealsByName(mealName: String): List<MealDTO>?

}