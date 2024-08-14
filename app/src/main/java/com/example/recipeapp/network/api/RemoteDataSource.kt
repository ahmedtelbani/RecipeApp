package com.example.recipeapp.network.api

class RemoteDataSource(
    private val api: RecipeApi
): IRemoteDataSource {
    override suspend fun getAllMeals(): List<MealDTO> {
        return api.getAllMeals().meals ?: emptyList()
    }

    override suspend fun getMealById(mealId: Int): MealDTO? {
        return api.getMealById(mealId).meals?.first()
    }

    override suspend fun searchMealsByName(mealName: String): List<MealDTO>? {
        return api.searchMealsByName(mealName).meals
    }
}