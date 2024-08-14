package com.example.recipeapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.ui.AuthActivity
import com.example.recipeapp.ui.MainActivity
import com.example.recipeapp.util.PreferencesHelper

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Handler(Looper.getMainLooper()).postDelayed({
            val navController = findNavController()
            val preferencesHelper = PreferencesHelper(requireContext())
            val userStatus = preferencesHelper.getValue()

            if (userStatus == 0) {

                navController.navigate(R.id.action_splashFragment_to_loginFragment)
            } else {

                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }, 3000)
    }
}