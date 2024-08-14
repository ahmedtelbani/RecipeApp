package com.example.recipeapp.network.api

interface IMealsRepository {

    suspend fun getAllMeals()

    suspend fun getMealById(mealId: Int): MealDTO?

    suspend fun searchMealsByName(mealName: String): List<MealDTO>?
}