package com.example.recipeapp.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentLoginBinding
import com.example.recipeapp.ui.viewmodel.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    val authViewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.logInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

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
                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()) && !password.matches(
                        "^(?=.*[A-Z])(?=(.*\\d){3,})(?=.{8,15}$).*$".toRegex()
                    )
                ) {
                    Snackbar.make(
                        binding.root,
                        "The email  and the password are not valid",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
                } else if (!password.matches("^(?=.*[A-Z])(?=(.*\\d){3,})(?=.{8,15}$).*$".toRegex())) {
                    Snackbar.make(
                        binding.root,
                        "The password must contain one capital letter, 3 numbers, and be between 8-15 characters",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.primaryColor))
                        .show()
                } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex())) {
                    Snackbar.make(
                        binding.root,
                        "The email is not  valid",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
                } else {
                    authViewModel.userLogin(email, password)
                    authViewModel.loginUser.observe(viewLifecycleOwner, Observer { user ->
                        if (user != null) {
                            Snackbar.make(
                                binding.root,
                                "Login successfull!",
                                Snackbar.LENGTH_LONG
                            ).setBackgroundTint(resources.getColor(R.color.primaryColor)).show()

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

    }

}