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
    private var meals: MutableList<Meal>,
    private val onDeleteClicked: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    inner class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mealImageView: ImageView = view.findViewById(R.id.mealImageView)
        val mealNameTextView: TextView = view.findViewById(R.id.mealNameTextView)
        val heartButton: ImageButton = view.findViewById(R.id.heartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.mealNameTextView.text = meal.strMeal
        Picasso.get().load(meal.strMealThumb).into(holder.mealImageView)

        holder.heartButton.setOnClickListener {
            onDeleteClicked(meal)
            removeMeal(position)

        }
    }
    fun updateMeals(newMeals: List<Meal>) {
        meals.clear()
        meals.addAll(newMeals)
        notifyDataSetChanged()
    }

    private fun removeMeal(position: Int) {
        meals.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, meals.size)
    }

    override fun getItemCount(): Int = meals.size
}