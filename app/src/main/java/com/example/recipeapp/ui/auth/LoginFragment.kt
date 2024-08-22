package com.example.recipeapp.ui.auth

import android.content.Intent
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
import com.example.recipeapp.databinding.FragmentLoginBinding
import com.example.recipeapp.ui.RecipeActivity
import com.example.recipeapp.ui.viewmodel.AuthViewModel
import com.example.recipeapp.util.AuthHelper
import com.example.recipeapp.util.PreferencesHelper
import com.example.recipeapp.util.Security
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navigationController: NavController
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        navigationController = findNavController()
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
            val authHelper = AuthHelper(email, password, password)

            if (email.isBlank()) {
                showSnackBar(getString(R.string.the_email_field_is_empty))
            } else if (password.isBlank()) {
                showSnackBar(getString(R.string.the_password_field_is_empty))
            } else if (!authHelper.isEmailValid()) {
                showSnackBar(getString(R.string.the_email_is_not_in_valid_form))
            } else if (!authHelper.isPasswordValid()) {
                showSnackBar(getString(R.string.password_validation_message))
            } else {  // everything perfect
                val hashedPassword = Security().hashPassword(password)
                authViewModel.isUserExistByEmail(email)
                authViewModel.userLogin(email, hashedPassword)
                authViewModel.isUserExist.observe(viewLifecycleOwner) { isUserExist ->
                    if (isUserExist) {
                        authViewModel.loginUser.observe(viewLifecycleOwner) { user ->
                            if (user != null) {
                                val intent = Intent(requireActivity(), RecipeActivity::class.java)
                                val preferences = PreferencesHelper(requireContext())
                                preferences.setValue(user.id)
                                startActivity(intent)
                                requireActivity().finish()
                            } else {
                                showSnackBar(getString(R.string.incorrect_password))
                            }
                        }
                    } else {
                        showSnackBar(getString(R.string.you_don_t_have_an_account))
                    }
                }
            }
        }  // end on click listener

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

    private fun showSnackBar(mess: String) {
        val bgColor = getColor(requireContext(), R.color.primaryColor)
        Snackbar.make(binding.root, mess, Snackbar.LENGTH_LONG)
            .setBackgroundTint(bgColor)
            .show()
    }

}