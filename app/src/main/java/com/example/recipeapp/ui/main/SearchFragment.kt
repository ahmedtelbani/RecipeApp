package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.extensions.textChanges
import com.example.recipeapp.ui.adapter.MealAdapter
import com.example.recipeapp.ui.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

class SearchFragment : Fragment(), MealAdapter.OnMealItemClickListener {

    private val recipeViewModel: RecipeViewModel by viewModels()

    private lateinit var searchEditText: EditText
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var emptySearchMessageTextView: TextView
    private lateinit var foodAdapter: MealAdapter

    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchEditText = view.findViewById(R.id.search_et_mealSearch)
        searchRecyclerView = view.findViewById(R.id.search_rv_mealList)
        emptySearchMessageTextView = view.findViewById(R.id.empty_search_message)
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
                    if (items != null) {
                        updateUI(items)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(items: List<Meal>) {
        if (items.isEmpty()) {
            emptySearchMessageTextView.visibility = View.VISIBLE
            searchRecyclerView.visibility = View.GONE
        } else {
            emptySearchMessageTextView.visibility = View.GONE
            searchRecyclerView.visibility = View.VISIBLE
            foodAdapter = MealAdapter(items, this, recipeViewModel)
            searchRecyclerView.adapter = foodAdapter
        }
    }


    override fun onMealItemClicked(meal: Meal) {
        val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(meal.idMeal)
        findNavController().navigate(action)
    }

}