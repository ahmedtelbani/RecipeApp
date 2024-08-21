package com.example.recipeapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Category
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.adapter.CategoryAdapter
import com.example.recipeapp.ui.adapter.MealAdapter
import com.example.recipeapp.ui.viewmodel.RecipeViewModel
import com.example.recipeapp.util.isInternetAvailable

class HomeFragment : Fragment(),
    MealAdapter.OnMealItemClickListener,
    CategoryAdapter.OnCategoryItemClickListener {

    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var mealAdapter: MealAdapter
      
    private lateinit var categoryTitleTextView: TextView
    private lateinit var emptyListMessageTextView: TextView
    private lateinit var recyclerView: RecyclerView
      
    private var originalMealList: List<Meal> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealAdapter = MealAdapter(listOf(), this, viewModel)
        recyclerView = view.findViewById(R.id.food_recycler_view)
        val categoryRecyclerView: RecyclerView = view.findViewById(R.id.category_recycler_view)
        categoryTitleTextView = view.findViewById(R.id.allMealsTextView)
        emptyListMessageTextView = view.findViewById(R.id.empty_list_message)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mealAdapter
        viewModel.allMealList.observe(viewLifecycleOwner) { meals ->
            if (meals.isEmpty()) {
                emptyListMessageTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyListMessageTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                mealAdapter.updateMeals(meals)
            }
            originalMealList = meals
        }

        viewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->
            val categoryAdapter = CategoryAdapter(categoryList, this)
            categoryRecyclerView.adapter = categoryAdapter
        }
        
        categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        if(isInternetAvailable(view.context)) {
            viewModel.getCategories()  // Fetch categories
            viewModel.getAllMeals()  // Fetch all meals
            Log.d("HOME_FRAGMENT", "There is internet connection")
        } else {
            Log.d("HOME_FRAGMENT", "There is no internet connection")
            val homeFragment  = view.findViewById<ConstraintLayout>(R.id.fragment_home)
            homeFragment.visibility = View.GONE
            val noInternetLinearLayout = view.findViewById<LinearLayout>(R.id.noInternetLinearLayout)
            noInternetLinearLayout.visibility = View.VISIBLE
        }
    }

    override fun onMealItemClicked(meal: Meal) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(meal.idMeal)
        findNavController().navigate(action)
    }

    override fun onCategoryItemClicked(category: Category) {
        if (category.strCategory == "Random Meal") {
            viewModel.getRandomMeal()
            viewModel.randomMealList.observe(viewLifecycleOwner) { randomMeals ->
                if (randomMeals.isEmpty()) {
                    emptyListMessageTextView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    emptyListMessageTextView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    mealAdapter.updateMeals(randomMeals)
                }
            }
        } else {
            mealAdapter.filterMeals(category.strCategory, originalMealList)
            if (mealAdapter.itemCount == 0) {
                emptyListMessageTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyListMessageTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
        categoryTitleTextView.text = category.strCategory
    }
}