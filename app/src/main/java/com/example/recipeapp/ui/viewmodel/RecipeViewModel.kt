package com.example.recipeapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.database.RecipeDatabase
import com.example.recipeapp.data.model.Category
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.model.ResponseObject
import com.example.recipeapp.data.repository.RecipeRepository
import com.example.recipeapp.data.network.api.IMealsRepository
import com.example.recipeapp.data.network.api.IRemoteDataSource
import com.example.recipeapp.data.network.api.MealsRepository
import com.example.recipeapp.data.network.api.RemoteDataSource
import com.example.recipeapp.data.network.api.RetrofitInstance
import com.example.recipeapp.util.PreferencesHelper
import com.example.recipeapp.util.handleApiResponseUiLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application): AndroidViewModel(application) {
    /**
     * Handles business logic for the Recipe-related UI.
     */

    private val recipeRepository: RecipeRepository

    private val remoteDataSource : IRemoteDataSource = RemoteDataSource(RetrofitInstance.api)
    private val repository: IMealsRepository = MealsRepository(remoteDataSource)

    private val _allMealList: MutableLiveData<List<Meal>> = MutableLiveData()
    val allMealList: LiveData<List<Meal>> get() = _allMealList

    private val _categoryList: MutableLiveData<List<Category>> = MutableLiveData()
    val categoryList: LiveData<List<Category>> get() = _categoryList

    private val _searchMealList: MutableLiveData<List<Meal>?> = MutableLiveData()
    val searchMealList: MutableLiveData<List<Meal>?> get() = _searchMealList

    private val _favoriteMealList: MutableLiveData<List<Meal>> = MutableLiveData()
    val favoriteMealList: LiveData<List<Meal>> get() = _favoriteMealList

    val randomMealList: LiveData<List<Meal>> get() = _randomMealList
    private val _randomMealList:MutableLiveData<List<Meal>> = MutableLiveData()
    private val sharedPreferences: PreferencesHelper = PreferencesHelper(application)


    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        val remoteDataSource = RemoteDataSource(RetrofitInstance.api)
        recipeRepository = RecipeRepository(recipeDao, remoteDataSource)
    }


    fun getAllMeals(){
        viewModelScope.launch {
            try {
                handleApiResponseUiLogic(
                    repository.getAllMeals(),
                    onSuccess = {
                        _allMealList.postValue(it.meals!!)
                    },
                    onHttpError = { code, message -> Log.d("boodyNew", "$code, $message")},
                    onUnknownError = {e -> Log.d("boodyNew", "${e.message}")},
                    onSomethingElseHappen = {Log.d("boodyNew", "Something Wrong Happened")}
                )
                /*val result = repository.getAllMeals()
                _allMealList.postValue(result)
                Log.i("getAllMeals",result.toString())*/
            } catch (e: Exception){
                Log.e("getAllMeals", e.toString())
            }
        }
    }

    fun getRandomMeal()
    {
        viewModelScope.launch {
            try{
                handleApiResponseUiLogic(
                    repository.getRandomMeal(),
                    onSuccess = { response ->
                        _randomMealList.postValue(response.meals!!)
                    },
                    onHttpError = { code, message -> Log.d("boodyNew", "$code, $message")},
                    onUnknownError = {e -> Log.d("boodyNew", "${e.message}")},
                    onSomethingElseHappen = {Log.d("boodyNew", "Something Wrong Happened")}
                )
            }catch(e:Exception)
            {
                Log.e("getRandomMeal", e.toString())
            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            try {
                val result = repository.getCategories()
                _categoryList.postValue(result)
                Log.i("getCategories",result.toString())
            }catch (e: Exception){
                Log.e("getCategories", e.toString())
            }
        }
    }

    fun searchForMeals(searchText: String){
        viewModelScope.launch {
            try {
                val response = repository.searchMealsByName(searchText)
                handleApiResponseUiLogic(
                    response,
                    onSuccess = { data: ResponseObject ->
                        // Check if meals exist in the response
                        if (data.meals != null && data.meals.isNotEmpty()) {
                            _searchMealList.postValue(data.meals)
                        } else {
                            // Post an empty list if no meals are found
                            _searchMealList.postValue(emptyList())
                        }
                    },
                    onHttpError = { code, message -> Log.d("boodyNew", "$code, $message")},
                    onUnknownError = {e -> Log.d("boodyNew", "${e.message}")},
                    onSomethingElseHappen = {Log.d("boodyNew", "Something Wrong Happened")}
                )
                /*val result = repository.searchMealsByName(searchText)
                _searchMealList.postValue(result ?: listOf<Meal>())*/
            }catch (e: Exception){
                Log.e("getAllMeals", e.toString())
                _searchMealList.postValue(emptyList())
            }
        }
    }

    fun addFavoriteMeal(meal: Meal) {
        val userId = sharedPreferences.getValue().toString()
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepository.addFavoriteMeal(meal, userId)
        }
    }

    fun getAllFavoriteMeals() {
        val userId = sharedPreferences.getValue().toString()
        viewModelScope.launch {
            _favoriteMealList.postValue(recipeRepository.getAllFavoriteMeals(userId))
        }
    }

    fun deleteFavoriteMeal(meal: Meal) {
        val userId = sharedPreferences.getValue().toString()
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepository.deleteFavoriteMeal(meal, userId)
            getAllFavoriteMeals()
        }
    }

    fun isMealFavorite(mealId: String): LiveData<Boolean> {
        val userId = sharedPreferences.getValue().toString()
        return recipeRepository.isMealFavorite(mealId, userId)
    }


    // ---------------------------------------------------------------------------------------------
    // Recipe Details Screen START
    // ---------------------------------------------------------------------------------------------

    private val _selectedRecipe: MutableLiveData<Meal?> = MutableLiveData()
    val selectedRecipe: LiveData<Meal?> = _selectedRecipe

    private val _showFullRecipe: MutableLiveData<Boolean> = MutableLiveData(false)
    val showFullRecipe: LiveData<Boolean> = _showFullRecipe


    fun getMealById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            handleApiResponseUiLogic(
                recipeRepository.getMealById(id.toInt()),
                onSuccess = { response ->
                    _selectedRecipe.postValue(
                        response.meals?.first()
                    )
                },
                onHttpError = { code, message -> Log.d("boodyNew", "$code, $message")},
                onUnknownError = {e -> Log.d("boodyNew", "${e.message}")},
                onSomethingElseHappen = {Log.d("boodyNew", "Something Wrong Happened")}
            )
        }
    }

    private fun setShowFullRecipe(value: Boolean) = _showFullRecipe.postValue(value)

    fun switchShowFullRecipe() {
        setShowFullRecipe(!showFullRecipe.value!!)
    }

    // ---------------------------------------------------------------------------------------------
    // Recipe Details Screen END
    // ---------------------------------------------------------------------------------------------



}



