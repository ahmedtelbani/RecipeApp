package com.example.recipeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal

class MealAdapter (
    private val foodList: List<Meal>,
    private val listener: OnMealItemClickListener
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

        holder.favoriteButton.setOnClickListener {
            // handle favorite action
        }
    }


    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage: ImageView = view.findViewById(R.id.mealImageView)
        val foodName: TextView = view.findViewById(R.id.mealNameTextView)
        val favoriteButton: ImageButton = view.findViewById(R.id.heartButton)
    }
}