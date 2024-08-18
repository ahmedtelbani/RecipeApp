package com.example.recipeapp.ui.auth

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.recipeapp.R
import com.example.recipeapp.data.model.User
import com.example.recipeapp.databinding.FragmentRegisterBinding
import com.example.recipeapp.ui.viewmodel.AuthViewModel
import com.example.recipeapp.util.AuthHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    val authViewModel: AuthViewModel by viewModels()
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpButton.setOnClickListener {
            var username = binding.usernameEditText.text.toString()
            var email = binding.emailEditText.text.toString()
            var password = binding.passwordEditText.text.toString()
            var confirmedPassword = binding.confirmPasswordEditText.text.toString()
            var authHelper = AuthHelper(email, password, confirmedPassword)
            if (username.isBlank() || password.isBlank() || email.isBlank() || confirmedPassword.isBlank()) {
                Snackbar.make(binding.root, "Please fill all the Inputs", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
            } else {
                if (!authHelper.isEmailValid() && !authHelper.isPasswordValid()
                ) {
                    Snackbar.make(
                        binding.root,
                        "wrong email and password format",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
                } else if (!authHelper.isEmailValid()) {
                    Snackbar.make(binding.root, "Invalid Email", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
                } else if (authHelper.isPasswordValid()) {
                    Snackbar.make(
                        binding.root,
                        "The password must contain at least one capital letter, one number and be between 8 - 15 characters",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.primaryColor))
                        .show()
                } else {
                    if (!authHelper.arePasswordsMatching()) {
                        Snackbar.make(
                            binding.root,
                            "The passwords doesn't match!",
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(resources.getColor(R.color.primaryColor))
                            .show()
                    } else {
                        authViewModel.isUserExistByEmail(email)
                        authViewModel.isUserExist.observe(viewLifecycleOwner, Observer {
                            if (it) {
                                Snackbar.make(
                                    binding.root,
                                    "A user with this Email already exists",
                                    Snackbar.LENGTH_LONG
                                ).setBackgroundTint(resources.getColor(R.color.primaryColor))
                                    .setAction("Login")
                                    {
                                        navController.navigate(R.id.action_registerFragment_to_loginFragment,
                                            null,
                                            navOptions {
                                                popUpTo(R.id.registerFragment)
                                                {
                                                    inclusive = true
                                                }
                                            })
                                    }
                                    .show()
                            } else {
                                authViewModel.addUser(
                                    User(
                                        0,
                                        name = username,
                                        email = email,
                                        hashedPassword = password,
                                        "Sunday"
                                    )
                                )
                                Snackbar.make(
                                    binding.root,
                                    "Account Created successfully",
                                    Snackbar.LENGTH_LONG
                                ).setBackgroundTint(resources.getColor(R.color.primaryColor))
                                    .show()
                                navController.navigate(R.id.action_registerFragment_to_loginFragment,
                                    null,
                                    navOptions {
                                        popUpTo(R.id.registerFragment) {
                                            inclusive = true
                                        }
                                    })
                            }
                        })
                    }
                }
            }

        }
        binding.loginTV.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment,
                null,
                navOptions {
                    popUpTo(R.id.registerFragment) {
                        inclusive = true
                    }
                })
        }
    }

}