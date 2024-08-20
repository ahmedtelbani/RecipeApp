package com.example.recipeapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.recipeapp.R
import com.example.recipeapp.ui.RecipeActivity
import com.example.recipeapp.util.PreferencesHelper

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // show the logo for 3.2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val navController = findNavController()
            val preferencesHelper = PreferencesHelper(requireContext())
            val userStatus = preferencesHelper.getValue()

            if (userStatus == 0L) {
                navController.navigate(R.id.action_splashFragment_to_loginFragment,
                    null,
                    navOptions {
                        popUpTo(R.id.splashFragment) {
                            inclusive = true
                        }
                    })
            } else {
                startActivity(Intent(requireActivity(), RecipeActivity::class.java))
                requireActivity().finish()
            }
        }, 3200)
    }
}