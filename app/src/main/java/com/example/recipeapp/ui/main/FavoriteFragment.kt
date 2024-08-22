package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.adapter.MealAdapter
import com.example.recipeapp.ui.viewmodel.RecipeViewModel

class FavoriteFragment : Fragment(), MealAdapter.OnMealItemClickListener {
    private val viewModel: RecipeViewModel by viewModels() // Initialize ViewModel with delegate
    private lateinit var favoriteAdapter: MealAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var toolbar: Toolbar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        recyclerView = view.findViewById(R.id.recyclerView)
        emptyTextView = view.findViewById(R.id.emptyTextView)

        toolbar.title = "Favorite"
        favoriteAdapter = MealAdapter(mutableListOf(), this, viewModel)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = favoriteAdapter

        viewModel.favoriteMealList.observe(viewLifecycleOwner) { meals ->
            if (meals.isEmpty()) {
                emptyTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                favoriteAdapter.updateMeals(meals)
            }
        }

        viewModel.getAllFavoriteMeals()
    }

    override fun onMealItemClicked(meal: Meal) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToRecipeDetailFragment(meal.idMeal)
        findNavController().navigate(action)
    }
}
