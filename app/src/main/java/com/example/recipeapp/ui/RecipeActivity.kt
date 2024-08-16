package com.example.recipeapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.recipeapp.R
import com.example.recipeapp.ui.main.FavoriteFragment
import com.example.recipeapp.ui.main.HomeFragment
import com.example.recipeapp.ui.main.SearchFragment
import com.example.recipeapp.ui.viewmodel.RecipeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecipeActivity : AppCompatActivity() {

    /**
     * Hosts the HomeFragment, FavoriteFragment, SearchFragment, RecipeDetailFragment, etc
     */

    private val recipeViewModel: RecipeViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_main) as NavHostFragment
        navController = navHostFragment.navController

        //Bottom Navigation View
        bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.navigation_search -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.navigation_favorite -> {
                    replaceFragment(FavoriteFragment())
                    true
                }
                else -> false
            }
        }
            replaceFragment(HomeFragment())
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_main,fragment).commit()
    }
}

