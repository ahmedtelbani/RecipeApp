package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.ui.main.adapter.FoodAdapter
import com.example.recipeapp.ui.viewmodel.RecipeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.food_recycler_view)
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        viewModel.favoriteMealList.observe(viewLifecycleOwner) { foodList ->
            recyclerView.adapter = FoodAdapter(foodList)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //TODO get All Meals
//        viewModel.getAllFavoriteMeals()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}



