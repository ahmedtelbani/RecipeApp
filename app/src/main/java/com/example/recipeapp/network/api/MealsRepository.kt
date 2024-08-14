package com.example.recipeapp.network.api

import com.example.recipeapp.data.model.Meal

class MealsRepository(
    private val remoteDataSource: IRemoteDataSource
): IMealsRepository {
    override suspend fun getAllMeals(): List<Meal> {
        return remoteDataSource.getAllMeals()
    }

    override suspend fun getMealById(mealId: Int): Meal? {
        return remoteDataSource.getMealById(mealId)
    }

    override suspend fun searchMealsByName(mealName: String): List<Meal>? {
        return remoteDataSource.searchMealsByName(mealName)
    }

}