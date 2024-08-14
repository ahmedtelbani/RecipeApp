package com.example.recipeapp.data.repository

import com.example.recipeapp.data.database.RecipeDao
import com.example.recipeapp.data.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(private val recipeDao: RecipeDao) {
    /**
     * Handles data operations, including fetching data from the network and the local database.
     */

    suspend fun addFavoriteMeal(meal: Meal) {
        recipeDao.addFavoriteMeal(meal)
    }

    suspend fun getAllFavoriteMeals(): List<Meal> = withContext(Dispatchers.IO) {
        recipeDao.getAllFavoriteMeals()
    }

    suspend fun getAllFavoriteMealsId(): List<String> = withContext(Dispatchers.IO) {
        recipeDao.getAllFavoriteMealsId()
    }

    suspend fun deleteFavoriteMeal(meal: Meal) {
        recipeDao.deleteFavoriteMeal(meal)
    }

}