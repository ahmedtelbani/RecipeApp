package com.example.recipeapp.network.api

import com.example.recipeapp.data.model.Categories
import com.example.recipeapp.data.model.Meal

interface IMealsRepository {

    suspend fun getAllMeals(): List<Meal>

    suspend fun getCategories(): List<Categories>

    suspend fun getMealById(mealId: Int): Meal?

    suspend fun searchMealsByName(mealName: String): List<Meal>?

    suspend fun getRandomMeal():List<Meal>
}