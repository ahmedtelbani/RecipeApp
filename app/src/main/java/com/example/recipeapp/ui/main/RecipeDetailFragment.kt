package com.example.recipeapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.model.getIngredients
import com.example.recipeapp.data.model.getMeasures
import com.example.recipeapp.ui.viewmodel.RecipeViewModel
import com.example.recipeapp.util.isInternetAvailable

class RecipeDetailFragment : Fragment() {

    private val args: RecipeDetailFragmentArgs by navArgs()
    private val viewModel: RecipeViewModel by viewModels()

    // UI elements
    private lateinit var recipeImageView: ImageView
    private lateinit var recipeTitleTextView: TextView
    private lateinit var recipeIconIsFavorateImageButton: ImageButton
    private lateinit var recipeAreaTextView: TextView
    private lateinit var recipeCategoryTextView: TextView
    private lateinit var recipeIngredientsListView: TextView
    private lateinit var recipeMeasuresListView: TextView
    private lateinit var recipeInstructionsTextView: TextView
    private lateinit var showMoreButton: Button
    private lateinit var recipeVideoWebView: WebView
    private lateinit var errorMessageTextView: TextView

    private lateinit var categoryTextView: TextView
    private lateinit var areaTextView: TextView
    private lateinit var instructionsTextView: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI(view)

        if(isInternetAvailable(view.context)) {
            // call the api via repository
            viewModel.getMealById(args.selectedMealId)
            // observe the results
            viewModel.selectedRecipe.observe(viewLifecycleOwner) {
                if(it != null) {
                    updateMealUI(it)
                }
            }

            showMoreButton.setOnClickListener {
                viewModel.switchShowFullRecipe()
            }

            viewModel.showFullRecipe.observe(viewLifecycleOwner) {
                setItemsVisibility(it)
            }

            errorMessageTextView.visibility = View.GONE
            showAnotherElements()
        } else {
            noInternet()
        }

    }

    private fun initializeUI(view: View) {
        recipeImageView = view.findViewById(R.id.iv_recipe_image)
        recipeTitleTextView = view.findViewById(R.id.tv_recipe_title)
        recipeIconIsFavorateImageButton = view.findViewById(R.id.imageButton)
        recipeCategoryTextView = view.findViewById(R.id.tv_recipe_catigory)
        recipeAreaTextView = view.findViewById(R.id.tv_recipe_area)
        recipeIngredientsListView = view.findViewById(R.id.tv_ingredients)
        recipeMeasuresListView = view.findViewById(R.id.tv_measures)
        recipeInstructionsTextView = view.findViewById(R.id.tv_recipe_instructions)
        showMoreButton = view.findViewById(R.id.btn_show_full_recipe)
        recipeVideoWebView = view.findViewById(R.id.wv_recipe_video)
        errorMessageTextView = view.findViewById(R.id.tv_error_message_recipe_details_fragment)

        categoryTextView = view.findViewById(R.id.textView4_category)
        areaTextView = view.findViewById(R.id.textView5_area)
        instructionsTextView = view.findViewById(R.id.textView7_instructions)
    }

    private fun updateMealUI(meal: Meal) {
        Glide
            .with(this)
            .load(meal.strMealThumb)
            .into(recipeImageView)

        recipeTitleTextView.text = meal.strMeal

        viewModel.isMealFavorite(meal.idMeal).observe(viewLifecycleOwner) { isFavorate ->
            recipeIconIsFavorateImageButton.setImageResource(
                if (isFavorate == true) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
            recipeIconIsFavorateImageButton.setOnClickListener {
                if (isFavorate == true) {
                    viewModel.deleteFavoriteMeal(meal)
                } else {
                    viewModel.addFavoriteMeal(meal)
                }
            }
        }

        recipeCategoryTextView.text = meal.strCategory
        recipeAreaTextView.text = meal.strArea
        recipeInstructionsTextView.text = meal.strInstructions

        val ingredients = meal.getIngredients()
        val measures = meal.getMeasures()
        val filteredIngredients = filterNotEmptyOrNotBlank(ingredients.toList())
        val filteredMeasures = filterNotEmptyOrNotBlank(measures.toList())

        Log.d("boody", filteredIngredients.toString())
        Log.d("boody", filteredMeasures.toString())

        recipeIngredientsListView.text = filteredIngredients.toString()
        recipeMeasuresListView.text = filteredMeasures.toString()



        val videoUrl = "<iframe width=\"100%\" height=\"100%\" src=\"${convertToEmbedUrl(meal.strYoutube!!)}\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
        recipeVideoWebView.loadData(videoUrl, "text/html", "utf-8")
        recipeVideoWebView.settings.javaScriptEnabled = true
        recipeVideoWebView.webChromeClient = WebChromeClient()


    }

    private fun filterNotEmptyOrNotBlank(list: List<String?>): List<String> {
        return list.filterNotNull().filter { it.isNotEmpty() && it.isNotBlank() }
    }

    private fun setItemsVisibility(isVisible: Boolean) {
        if(!isVisible) {
            recipeMeasuresListView.visibility = View.GONE
            recipeIngredientsListView.visibility = View.GONE
            recipeInstructionsTextView.visibility = View.GONE
            showMoreButton.text = getString(R.string.show_more)
        } else {
            recipeMeasuresListView.visibility = View.VISIBLE
            recipeIngredientsListView.visibility = View.VISIBLE
            recipeInstructionsTextView.visibility = View.VISIBLE
            showMoreButton.text = getString(R.string.show_less)
        }

    }

    private fun convertToEmbedUrl(youtubeUrl: String): String {
        val videoIdRegex = Regex("(?<=v=)[\\w-]+")
        val videoId = videoIdRegex.find(youtubeUrl)?.value

        return if (videoId != null) {
            "https://www.youtube.com/embed/$videoId"
        } else {
            throw IllegalArgumentException("Invalid YouTube URL")
        }
    }

    private fun noInternet() {
        Log.d("boodyNew", "NoInternet")
        if (errorMessageTextView.visibility == View.GONE) {
            errorMessageTextView.visibility = View.VISIBLE
        }
        hideAnotherElements()
    }
    private fun hideAnotherElements() {
        categoryTextView.visibility = View.GONE
        areaTextView.visibility = View.GONE
        instructionsTextView.visibility = View.GONE
        showMoreButton.visibility = View.GONE
        recipeIconIsFavorateImageButton.visibility = View.GONE
    }
    private fun showAnotherElements() {
        categoryTextView.visibility = View.VISIBLE
        areaTextView.visibility = View.VISIBLE
        instructionsTextView.visibility = View.VISIBLE
        showMoreButton.visibility = View.VISIBLE
        recipeIconIsFavorateImageButton.visibility = View.VISIBLE
    }
}