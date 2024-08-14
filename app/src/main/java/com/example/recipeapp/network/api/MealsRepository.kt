package com.example.recipeapp.network.api

class MealsRepository(
    private val remoteDataSource: IRemoteDataSource
): IMealsRepository {
    override suspend fun getAllMeals(): List<MealDTO> {
        return remoteDataSource.getAllMeals()
    }

    override suspend fun getMealById(mealId: Int): MealDTO? {
        return remoteDataSource.getMealById(mealId)
    }

    override suspend fun searchMealsByName(mealName: String): List<MealDTO>? {
        return remoteDataSource.searchMealsByName(mealName)
    }

}