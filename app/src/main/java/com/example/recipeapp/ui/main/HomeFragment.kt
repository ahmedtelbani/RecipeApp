package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.adapter.MealAdapter
import com.example.recipeapp.ui.viewmodel.RecipeViewModel

class HomeFragment : Fragment(), MealAdapter.OnMealItemClickListener {
    private val viewModel: RecipeViewModel by viewModels() // ViewModel initialization

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

        val recyclerView: RecyclerView = view.findViewById(R.id.food_recycler_view)

        // Pass the ViewModel to the adapter
        val mealAdapter = MealAdapter(listOf(), this, viewModel)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mealAdapter

        // Observe the meals from ViewModel and update the adapter
        viewModel.allMealList.observe(viewLifecycleOwner) { meals ->
            mealAdapter.updateMeals(meals)
        }

        viewModel.getAllMeals() // Fetch all meals
    }

    override fun onMealItemClicked(meal: Meal) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(meal.idMeal)
        findNavController().navigate(action)
    }
}