package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.viewmodel.RecipeViewModel

class RecipeDetailFragment : Fragment() {

    val args: RecipeDetailFragmentArgs by navArgs()
    val viewModel: RecipeViewModel by viewModels()

    // UI elements



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TO DO
        // call the api via repository
        viewModel.getMealById(args.selectedMealId)
        // observe the results
        viewModel.selectedRecipe.observe(viewLifecycleOwner) {
            if(it != null) {
                updateMealUI(it)
            }
        }
    }

    private fun updateMealUI(meal: Meal) {
        TODO("not implemented yet")
    }
}