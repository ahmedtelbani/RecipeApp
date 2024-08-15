package com.example.recipeapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.recipeapp.R
import com.example.recipeapp.data.model.User
import com.example.recipeapp.databinding.FragmentRegisterBinding
import com.example.recipeapp.ui.viewmodel.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    val authViewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpButton.setOnClickListener {
            var username = binding.usernameEditText.text.toString()
            var email = binding.emailEditText.text.toString()
            var password = binding.passwordEditText.text.toString()
            var confirmedPassword = binding.confirmPasswordEditText.text.toString()
            if (username.isBlank() || password.isBlank() || email.isBlank() || confirmedPassword.isBlank()) {
                Snackbar.make(binding.root, "Please fill all the Inputs", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
            } else {
                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()) && !password.matches(
                        "^(?=.*[A-Z])(?=(.*\\d){3,})(?=.{8,15}$).*$".toRegex()
                    )
                ) {
                    Snackbar.make(
                        binding.root,
                        "wrong email and password format",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
                } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex())) {
                    Snackbar.make(binding.root, "Invalid Email", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(resources.getColor(R.color.primaryColor)).show()
                } else if (!password.matches(
                        "^(?=.*[A-Z])(?=(.*\\d){3,})(?=.{8,15}$).*$".toRegex()
                    )
                ) {
                    Snackbar.make(
                        binding.root,
                        "The password must contain one capital letter, 3 numbers, and be between 8-15 characters",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.primaryColor))
                        .show()
                }
                else
                {
                    if(password != confirmedPassword)
                    {
                        Snackbar.make(
                            binding.root,
                            "The passwords doesn't match!",
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(resources.getColor(R.color.primaryColor))
                            .show()
                    }
                    else
                    {
                        authViewModel.isUserExistByEmail(email)
                        authViewModel.isUserExist.observe(viewLifecycleOwner, Observer {
                            if(it)
                            {
                                Snackbar.make(
                                    binding.root,
                                    "A user with this Email already exists",
                                    Snackbar.LENGTH_LONG
                                ).setBackgroundTint(resources.getColor(R.color.primaryColor))
                                    .setAction("Login")
                                    {
//                                        parentFragmentManager.beginTransaction().replace(R.id.fragment_container_view, LoginFragment()).commit()
                                    }
                                    .show()
                            }
                            else
                            {
                             authViewModel.addUser(User(0, name = username, email = email, hashedPassword = password, "Monday"))
                            }
                        })
                    }
                }
            }

        }
    }

}