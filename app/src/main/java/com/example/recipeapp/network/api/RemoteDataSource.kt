package com.example.recipeapp.network.api

import com.example.recipeapp.data.model.Meal

class RemoteDataSource(
    private val api: RecipeApi
): IRemoteDataSource {
    override suspend fun getAllMeals(): List<Meal> {
        return api.getAllMeals().meals ?: emptyList()
    }

    override suspend fun getMealById(mealId: Int): Meal? {
        return api.getMealById(mealId).meals?.first()
    }

    override suspend fun searchMealsByName(mealName: String): List<Meal>? {
        return api.searchMealsByName(mealName).meals
    }
}