package com.example.recipeapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.squareup.picasso.Picasso
import com.example.recipeapp.data.model.Meal

class MealAdapter(
    private val onRemoveFavorite: (Meal) -> Unit
) : ListAdapter<Meal, MealAdapter.MealViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)
    }

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealImageView: ImageView = itemView.findViewById(R.id.mealImageView)
        private val mealNameTextView: TextView = itemView.findViewById(R.id.mealNameTextView)
        private val heartButton: ImageButton = itemView.findViewById(R.id.heartButton)

        fun bind(meal: Meal) {
            mealNameTextView.text = meal.strMeal
            Picasso.get()
                .load(meal.strImageSource)
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_wifi_tethering_error_24)
                .into(mealImageView)

            heartButton.setOnClickListener {
                onRemoveFavorite(meal)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
}