package com.example.recipeapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.recipeapp.R
import com.example.recipeapp.data.model.User
import com.example.recipeapp.databinding.FragmentRegisterBinding
import com.example.recipeapp.ui.viewmodel.AuthViewModel
import com.example.recipeapp.util.AuthHelper
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTV.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment,
                null,
                navOptions {
                    popUpTo(R.id.registerFragment) {
                        inclusive = true
                    }
                })
        }

        binding.signUpButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmedPassword = binding.confirmPasswordEditText.text.toString()
            val authHelper = AuthHelper(email, password, confirmedPassword)

            if (username.isBlank() || password.isBlank() || email.isBlank() || confirmedPassword.isBlank()) {
                showSnackBar(getString(R.string.please_fill_all_the_input_fields))
            } else if (!authHelper.isEmailValid()) {
                showSnackBar(getString(R.string.the_email_is_not_in_valid_form))
            } else if (!authHelper.isPasswordValid()) {
                showSnackBar(getString(R.string.password_validation_message))
            } else if (!authHelper.arePasswordsMatching()) {
                showSnackBar(getString(R.string.the_passwords_don_t_match))
            } else {
                    authViewModel.isUserExistByEmail(email)
                    authViewModel.isUserExist.observe(viewLifecycleOwner) { user ->
                        if (user) {  // User already exists
                            showSnackBarWithLoginButton(getString(R.string.this_email_is_already_exists))
                        } else {  // everything perfect (Create new User)
                            authViewModel.addUser(
                                User(
                                    id = 0,
                                    name = username,
                                    email = email,
                                    hashedPassword = password,
                                    lastLoginTimestamp = authHelper.getCurrentTimestamp().toString()
                                )
                            )
                            showSnackBar(getString(R.string.account_created_successfully))
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

        }

    }

    private fun showSnackBar(mess: String) {
        val bgColor = getColor(requireContext(), R.color.primaryColor)
        Snackbar.make(binding.root, mess, Snackbar.LENGTH_LONG)
            .setBackgroundTint(bgColor)
            .show()
    }

    private fun showSnackBarWithLoginButton(mess: String) {
        val bgColor = getColor(requireContext(), R.color.primaryColor)
        Snackbar.make(binding.root, mess, Snackbar.LENGTH_LONG)
            .setBackgroundTint(bgColor)
            .setAction(getString(R.string.login)) {
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
    }

}