package com.example.recipeapp.util

import android.content.Context
import android.content.Intent
import android.net.Uri

class AboutHelper {

    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

}