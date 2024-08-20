package com.example.recipeapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Developer

class DeveloperAdapter(private val developers: List<Developer>) :
    RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder>() {

    class DeveloperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvDeveloperName)
        val linkedInIcon: ImageView = itemView.findViewById(R.id.ivLinkedInIcon)
        val gitHubIcon: ImageView = itemView.findViewById(R.id.ivGitHubIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_developer, parent, false)
        return DeveloperViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DeveloperViewHolder, position: Int) {
        val developer = developers[position]
        holder.nameTextView.text = developer.name

        holder.linkedInIcon.setOnClickListener {
            openUrl(holder.itemView.context, developer.linkedInUrl)
        }

        holder.gitHubIcon.setOnClickListener {
            openUrl(holder.itemView.context, developer.gitHubUrl)
        }
    }

    override fun getItemCount() = developers.size

    private fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}