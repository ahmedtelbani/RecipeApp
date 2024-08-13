package com.example.recipeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.database.RecipeDatabase
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application): AndroidViewModel(application) {
    /**
     * Handles business logic for the Recipe-related UI.
     */

    private val recipeRepository: RecipeRepository

    private val _favoriteMealList: MutableLiveData<List<Meal>> = MutableLiveData()
    val favoriteMealList: LiveData<List<Meal>> get() = _favoriteMealList

    private val _favoriteMealListIds: MutableLiveData<List<String>> = MutableLiveData()
    val favoriteMealListIds: LiveData<List<String>> get() = _favoriteMealListIds


    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        recipeRepository = RecipeRepository(recipeDao)
    }


    fun addFavoriteMeal(meal: Meal): String {
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepository.addFavoriteMeal(meal)
        }
        return meal.idMeal
    }

    fun getAllFavoriteMeals() {
        viewModelScope.launch {
            _favoriteMealList.postValue(recipeRepository.getAllFavoriteMeals())
        }
    }

    fun getAllFavoriteMealsId() {
        viewModelScope.launch {
            _favoriteMealListIds.postValue(recipeRepository.getAllFavoriteMealsId())
        }
    }

    fun deleteFavoriteMeal(meal: Meal): String {
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepository.deleteFavoriteMeal(meal)
        }
        return meal.idMeal
    }


}