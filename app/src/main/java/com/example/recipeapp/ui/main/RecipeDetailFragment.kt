package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.model.getIngredients
import com.example.recipeapp.data.model.getMeasures
import com.example.recipeapp.ui.viewmodel.RecipeViewModel

class RecipeDetailFragment : Fragment() {

    val args: RecipeDetailFragmentArgs by navArgs()
    val viewModel: RecipeViewModel by viewModels()

    // UI elements
    lateinit var recipeImageView: ImageView
    lateinit var recipeTitleTextView: TextView
    lateinit var recipeAreaTextView: TextView
    lateinit var recipeCategoryTextView: TextView
    lateinit var recipeIngredientsListView: ListView
    lateinit var recipeMeasuresListView: ListView
    lateinit var recipeInstructionsTextView: TextView
    lateinit var showMoreButton: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI(view)
        // call the api via repository
        viewModel.getMealById(args.selectedMealId)
        // observe the results
        viewModel.selectedRecipe.observe(viewLifecycleOwner) {
            if(it != null) {
                updateMealUI(it)
            }
        }
    }

    private fun initializeUI(view: View) {
        recipeImageView = view.findViewById(R.id.iv_recipe_image)
        recipeTitleTextView = view.findViewById(R.id.tv_recipe_title)
        recipeCategoryTextView = view.findViewById(R.id.tv_recipe_catigory)
        recipeAreaTextView = view.findViewById(R.id.tv_recipe_area)
        recipeIngredientsListView = view.findViewById(R.id.lv_ingredients)
        recipeMeasuresListView = view.findViewById(R.id.lv_measures)
        recipeInstructionsTextView = view.findViewById(R.id.tv_recipe_instructions)
        showMoreButton = view.findViewById(R.id.btn_show_full_recipe)
    }

    private fun updateMealUI(meal: Meal) {
        Glide
            .with(this)
            .load(meal.strMealThumb)
            .into(recipeImageView)

        recipeTitleTextView.text = meal.strMeal
        recipeCategoryTextView.text = meal.strCategory
        recipeAreaTextView.text = meal.strArea
        recipeInstructionsTextView.text = meal.strInstructions

        val ingredients = meal.getIngredients()
        val measures = meal.getMeasures()
        val filteredIngredients = filterNotEmptyOrNotBlank(ingredients.toList())
        val filteredMeasures = filterNotEmptyOrNotBlank(measures.toList())

        val ingredientsListAdapter = this.context?.let {
            ArrayAdapter(
                it, android.R.layout.simple_list_item_1, filteredIngredients
            )
        }

        val measuresListAdapter = this.context?.let {
            ArrayAdapter(
                it, android.R.layout.simple_list_item_1, filteredMeasures
            )
        }
        recipeIngredientsListView.adapter = ingredientsListAdapter
        recipeMeasuresListView.adapter = measuresListAdapter

    }

    private fun filterNotEmptyOrNotBlank(list: List<String>): List<String> {
        return list.filter { it.isNotEmpty() || it.isNotBlank() }
    }
}