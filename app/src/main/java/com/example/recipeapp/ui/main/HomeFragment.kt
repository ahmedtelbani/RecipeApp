package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Categories
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.adapter.CategoryAdapter
import com.example.recipeapp.ui.adapter.MealAdapter
import com.example.recipeapp.ui.viewmodel.RecipeViewModel
import com.example.recipeapp.util.isInternetAvailable

class HomeFragment : Fragment(),
    MealAdapter.OnMealItemClickListener,
    CategoryAdapter.OnCategoryItemClickListener{
    private val viewModel: RecipeViewModel by viewModels() // ViewModel initialization
    private lateinit var mealAdapter: MealAdapter
    private lateinit var categoryTitleTextView: TextView
    private var originalMealList: List<Meal> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pass the ViewModel to the adapter
        mealAdapter = MealAdapter(listOf(), this, viewModel)
        val recyclerView: RecyclerView = view.findViewById(R.id.food_recycler_view)
        val categoryRecyclerView : RecyclerView = view.findViewById(R.id.category_recycler_view)
        categoryTitleTextView = view.findViewById(R.id.allMealsTextView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mealAdapter

        // Observe the meals from ViewModel and update the adapter
        viewModel.allMealList.observe(viewLifecycleOwner) { meals ->
            originalMealList = meals
            mealAdapter.updateMeals(meals)
        }

        viewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->
            val categoryAdapter = CategoryAdapter(categoryList, this)
            categoryRecyclerView.adapter = categoryAdapter
        }
        categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        if(isInternetAvailable(view.context)) {
            //Fetch categories
            viewModel.getCategories()
            // Fetch all meals
            viewModel.getAllMeals()
        } else {
            // Handle No internet
        }



    }

    override fun onMealItemClicked(meal: Meal) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(meal.idMeal)
        findNavController().navigate(action)
    }

    override fun onCategoryItemClicked(category: Categories) {
        if (category.strCategory == "Random Meal") {
            viewModel.getRandomMeal()
            viewModel.randomMealList.observe(viewLifecycleOwner) { randomMeals ->
                mealAdapter.updateMeals(randomMeals)
            }
        }
        else
            mealAdapter.filterMeals(category.strCategory,originalMealList)
        categoryTitleTextView.text = category.strCategory
    }
//    fun showAllMeals() {
//        mealAdapter.filterMeals(null)
//        categoryTitleTextView.text = "All Meals"
//    }

}