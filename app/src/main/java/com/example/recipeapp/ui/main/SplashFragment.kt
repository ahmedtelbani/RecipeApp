package com.example.recipeapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.recipeapp.R
import com.example.recipeapp.ui.AuthActivity
import com.example.recipeapp.ui.MainActivity
import com.example.recipeapp.util.PreferencesHelper

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Handler(Looper.getMainLooper()).postDelayed({
            val navController = findNavController()
            val preferencesHelper = PreferencesHelper(requireContext())
            val userStatus = preferencesHelper.getValue()
//            preferencesHelper.setValue(1)

            if (userStatus == 0L) {
                navController.navigate(R.id.action_splashFragment_to_loginFragment,
                    null,
                    navOptions {
                        popUpTo(R.id.splashFragment) {
                            inclusive = true
                        }
                    })
            } else {
                Log.d("SplashFragment", "Navigating to MainActivity")

                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }, 3000)
    }
}