package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.ui.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    private val viewModel: RecipeViewModel by viewModels ()
    private lateinit var favoriteAdapter: MealAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var toolbar: Toolbar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        recyclerView = view.findViewById(R.id.recyclerView)
        emptyTextView = view.findViewById(R.id.emptyTextView)

        setupToolbar()
        setupRecyclerView()

        viewModel.favoriteMealList.observe(viewLifecycleOwner, Observer { meals ->
            if (meals.isEmpty()) {
                emptyTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                favoriteAdapter.submitList(meals)
            }
        })

        // Fetch all favorite meals when the fragment is created
        viewModel.getAllFavoriteMeals()
    }

    private fun setupToolbar() {
        toolbar.title = "Favorite Meals"
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)}
    }

    private fun setupRecyclerView() {
        favoriteAdapter = MealAdapter { meal ->
            viewModel.deleteFavoriteMeal(meal)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }
    }
}