package com.example.recipeapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recipeapp.R
import com.example.recipeapp.data.model.User
import com.example.recipeapp.ui.viewmodel.AuthViewModel

class AuthActivity : AppCompatActivity() {

    /**
     * Hosts the SplashFragment, LoginFragment, and RegisterFragment
     */

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // TO DO
        authViewModel.addUser(User(0, "Ahmed", "a@yahoo.com", "ss", "ss"))
        // authViewModel.addUser(User(0, "Mohamed", "m@yahoo.com", "mm", "mm"))
        authViewModel.deleteUser(User(3, "Ahmed", "a@yahoo.com", "ss", "ss"))
        authViewModel.isUserExistByEmail("a@yahoo.com")
        authViewModel.isUserExist.observe(this) { isExist->
            if(isExist) {
                Toast.makeText(this, "exist", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "no", Toast.LENGTH_SHORT).show()
            }
        }
    }
}