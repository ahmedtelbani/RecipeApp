package com.example.recipeapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.database.RecipeDatabase
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.repository.RecipeRepository
import com.example.recipeapp.network.api.IMealsRepository
import com.example.recipeapp.network.api.IRemoteDataSource
import com.example.recipeapp.network.api.MealsRepository
import com.example.recipeapp.network.api.RemoteDataSource
import com.example.recipeapp.network.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel(application: Application): AndroidViewModel(application) {
    /**
     * Handles business logic for the Recipe-related UI.
     */

    private val recipeRepository: RecipeRepository

    private val remoteDataSource : IRemoteDataSource = RemoteDataSource(RetrofitInstance.api)
    private val repository:IMealsRepository = MealsRepository(remoteDataSource)

    private val _allMealList: MutableLiveData<List<Meal>> = MutableLiveData()
    val allMealList: LiveData<List<Meal>> get() = _allMealList

    private val _searchMealList: MutableLiveData<List<Meal>> = MutableLiveData()
    val searchMealList: LiveData<List<Meal>> get() = _searchMealList

    private val _favoriteMealList: MutableLiveData<List<Meal>> = MutableLiveData()
    val favoriteMealList: LiveData<List<Meal>> get() = _favoriteMealList

    private val _favoriteMealListIds: MutableLiveData<List<String>> = MutableLiveData()
    val favoriteMealListIds: LiveData<List<String>> get() = _favoriteMealListIds


    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        val remoteDataSource = RemoteDataSource(RetrofitInstance.api)
        recipeRepository = RecipeRepository(recipeDao, remoteDataSource)
    }


    fun getAllMeals(){
        viewModelScope.launch {
            try {
                val result = repository.getAllMeals()
                _allMealList.postValue(result)
                Log.i("getAllMeals",result.toString())
            }catch (e: Exception){
                Log.e("getAllMeals", e.toString())
            }
        }
    }

    fun searchForMeals(searchText: String){
        viewModelScope.launch {
            try {
                val result = repository.searchMealsByName(searchText)
                _searchMealList.postValue(result ?: listOf<Meal>())
            }catch (e: Exception){
                Log.e("getAllMeals", e.toString())
            }
        }
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

    // ---------------------------
    // Recipe Details Screen
    // ---------------------------

    private val _selectedRecipe: MutableLiveData<Meal?> = MutableLiveData()
    val selectedRecipe: LiveData<Meal?> = _selectedRecipe

    private val _showFullRecipe: MutableLiveData<Boolean> = MutableLiveData(false)
    val showFullRecipe: LiveData<Boolean> = _showFullRecipe


    fun getMealById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedMeal = recipeRepository.getMealById(id.toInt())
            // switched context because i can't post value on IO thread
            withContext(Dispatchers.Main) {
                _selectedRecipe.postValue(
                    selectedMeal
                )
            }
        }
    }

    private fun setShowFullRecipe(value: Boolean) = _showFullRecipe.postValue(value)

    fun switchShowFullRecipe() {
        setShowFullRecipe(!showFullRecipe.value!!)
    }


}