package com.example.recipeapp.network.api

import com.example.recipeapp.data.model.Meal

interface IMealsRepository {

    suspend fun getAllMeals(): List<Meal>

    suspend fun getMealById(mealId: Int): Meal?

    suspend fun searchMealsByName(mealName: String): List<Meal>?
}