package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.adapter.FoodAdapter
import com.example.recipeapp.ui.viewmodel.RecipeViewModel

class HomeFragment : Fragment(), FoodAdapter.OnMealItemClickListener {
    private lateinit var viewModel: RecipeViewModel


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
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        viewModel.allMealList.observe(viewLifecycleOwner) { foodList ->
            recyclerView.adapter = FoodAdapter(foodList, this)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //get All Meals
        viewModel.getAllMeals()
    }

    override fun onMealItemClicked(meal: Meal) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(meal.idMeal)
        findNavController().navigate(action)
    }
}