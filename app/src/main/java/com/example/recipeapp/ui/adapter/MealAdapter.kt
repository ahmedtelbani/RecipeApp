package com.example.recipeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.viewmodel.RecipeViewModel

class MealAdapter (
    private var foodList: List<Meal>,
    private val listener: OnMealItemClickListener,
    private val viewModel: RecipeViewModel // Pass the ViewModel to the adapter

    ): RecyclerView.Adapter<MealAdapter.FoodViewHolder>() {

    interface OnMealItemClickListener {
        fun onMealItemClicked(meal: Meal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount() = foodList.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]

        holder.foodName.text = foodItem.strMeal

        Glide.with(holder.foodImage.context)
            .load(foodItem.strMealThumb)
            .into(holder.foodImage)


        holder.itemView.setOnClickListener {
            listener.onMealItemClicked(foodItem)
        }

        viewModel.isMealFavorite(foodItem.idMeal).observe(holder.itemView.context as LifecycleOwner) { isFavorite ->
            holder.favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )

            holder.favoriteButton.setOnClickListener {
                if (isFavorite) {
                    viewModel.deleteFavoriteMeal(foodItem)
                } else {
                    viewModel.addFavoriteMeal(foodItem)
                }
            }
        }
    }

    fun updateMeals(newMeals: List<Meal>) {
        this.foodList = newMeals
        notifyDataSetChanged()  // Notify the adapter that the data has changed
    }


    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage: ImageView = view.findViewById(R.id.mealImageView)
        val foodName: TextView = view.findViewById(R.id.mealNameTextView)
        val favoriteButton: ImageButton = view.findViewById(R.id.heartButton)
    }
}