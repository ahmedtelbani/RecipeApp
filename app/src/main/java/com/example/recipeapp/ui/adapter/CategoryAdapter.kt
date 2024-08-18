package com.example.recipeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Categories

class CategoryAdapter(
    private val categoryList: List<Categories>,
    private val listener: OnCategoryItemClickListener
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    interface OnCategoryItemClickListener {
        fun onCategoryItemClicked(category: Categories)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = categoryList[position]
        holder.foodName.text = categoryItem.strCategory

        Glide.with(holder.categoryImage.context)
            .load(categoryItem.strCategoryThumb)
            .into(holder.categoryImage)

        holder.itemView.setOnClickListener {
            listener.onCategoryItemClicked(categoryItem)
        }

    }

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryImage: ImageView = view.findViewById(R.id.categoryImageView)
        val foodName: TextView = view.findViewById(R.id.categoryNameTextView)
    }
}