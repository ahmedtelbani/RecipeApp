package com.example.recipeapp.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentLoginBinding
import com.example.recipeapp.ui.MainActivity
import com.example.recipeapp.ui.viewmodel.AuthViewModel
import com.example.recipeapp.util.AuthHelper
import com.example.recipeapp.util.PreferencesHelper
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    val authViewModel: AuthViewModel by viewModels()
    lateinit var navigationController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        navigationController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val authHelper = AuthHelper(email, password, password)
            if (email.isBlank() && password.isBlank()) {
                Snackbar.make(
                    binding.root,
                    "The Email and the password are Empty",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
            } else if (password.isBlank()) {
                Snackbar.make(binding.root, "The password is Empty", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
            } else if (email.isBlank()) {
                Snackbar.make(binding.root, "The email is Empty", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
            } else {
                if (!authHelper.isEmailValid() && !authHelper.isPasswordValid()) {
                    Snackbar.make(
                        binding.root,
                        "The email  and the password are not valid",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
                } else if (!authHelper.isPasswordValid()) {
                    Snackbar.make(
                        binding.root,
                        "The password must contain at least one capital letter, one number and be between 8 - 15 characters",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.primaryColor))
                        .show()
                } else if (!authHelper.isEmailValid()) {
                    Snackbar.make(
                        binding.root,
                        "The email is not  valid",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
                } else {
                    authViewModel.userLogin(email, password)
                    authViewModel.loginUser.observe(viewLifecycleOwner, Observer { user ->
                        if (user != null) {
                            var preferences = PreferencesHelper(requireContext())
                            preferences.setValue(user.id)
                            var intent = Intent(requireActivity(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()

                        } else {
                            Snackbar.make(
                                binding.root,
                                "You don't have an account",
                                Snackbar.LENGTH_LONG
                            ).setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
                        }
                    })
                }
            }

        }
        binding.signUp.setOnClickListener {
            navigationController.navigate(R.id.action_loginFragment_to_registerFragment,
                null,
                navOptions {
                    popUpTo(R.id.loginFragment) {
                        inclusive = true
                    }
                })
        }

    }

}