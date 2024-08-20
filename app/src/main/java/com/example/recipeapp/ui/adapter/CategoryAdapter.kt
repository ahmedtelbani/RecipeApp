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
    private var categoryList: List<Categories>,
    private val listener: OnCategoryItemClickListener
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    companion object {
        private val allMealsCategory = Categories(
            idCategory = "all_meals",
            strCategory = "All Meals",
            strCategoryThumb  = "https://mymetabolicmeals.com/cdn/shop/files/Home_SupportImages_Meal-Collage_ba7b3fcf-68bb-4952-aea2-9920385ec6fa.png?v=1673542356",
            strCategoryDescription = "All meals combined"
        )
        private val randomMealCategory = Categories(
            idCategory = "random_meal",
            strCategory = "Random Meal",
            strCategoryThumb = "https://www.listchallenges.com/f/lists/ae0b84a5-e7d2-41e8-8d4a-ad5fa0d60ae2.jpg",
            strCategoryDescription = "Random meal"
        )
    }

    init {
        categoryList = listOf(allMealsCategory, randomMealCategory) + categoryList
    }

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
        holder.categoryName.text = categoryItem.strCategory

        Glide.with(holder.categoryImage.context)
            .load(categoryItem.strCategoryThumb)
            .into(holder.categoryImage)

        holder.itemView.setOnClickListener {
            listener.onCategoryItemClicked(categoryItem)
        }

    }

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryImage: ImageView = view.findViewById(R.id.categoryImageView)
        val categoryName: TextView = view.findViewById(R.id.categoryNameTextView)
    }
}