package com.example.recipeapp.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.extensions.textChanges
import com.example.recipeapp.ui.adapter.FoodAdapter
import com.example.recipeapp.ui.viewmodel.RecipeViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

class SearchFragment : Fragment(), FoodAdapter.OnMealItemClickListener {

    private val recipeViewModel: RecipeViewModel by viewModels()

    private lateinit var searchEditText: EditText
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var foodAdapter: FoodAdapter

    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchEditText = view.findViewById(R.id.search_et_mealSearch)
        searchRecyclerView = view.findViewById(R.id.search_rv_mealList)
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText.textChanges().onEach { query ->
            performSearch(query)
        }
            .launchIn(lifecycleScope)
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()  // Cancel the previous job if it's still running
        searchJob = lifecycleScope.launch {
            try {
                recipeViewModel.searchForMeals(query)  // get data from API
                recipeViewModel.searchMealList.observe(requireActivity()) { items ->
                    updateUI(items)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(items: List<Meal>) {
        foodAdapter = FoodAdapter(items, this)
        searchRecyclerView.adapter = foodAdapter
    }

    override fun onMealItemClicked(meal: Meal) {
        val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(meal.idMeal)
        findNavController().navigate(action)
    }

}