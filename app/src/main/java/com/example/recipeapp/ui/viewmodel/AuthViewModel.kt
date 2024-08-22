package com.example.recipeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.database.RecipeDatabase
import com.example.recipeapp.data.model.User
import com.example.recipeapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application): AndroidViewModel(application) {
    /**
     * Handles business logic for authentication.
     */

    private val userRepository: UserRepository

    private val _isUserExist: MutableLiveData<Boolean> = MutableLiveData()
    val isUserExist: LiveData<Boolean> get() = _isUserExist

    private val _loginUser: MutableLiveData<User> = MutableLiveData()
    val loginUser: LiveData<User> get() = _loginUser

    private val _userByEmail : MutableLiveData<User> = MutableLiveData()

    val  userByEmail : LiveData<User>  get() = _userByEmail

    init {
        val userDao = RecipeDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }


    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.addUser(user)
        }
    }

    fun isUserExistByEmail(userEmail: String) {
        viewModelScope.launch {
            _isUserExist.postValue(userRepository.checkIfUserExistsByEmail(userEmail))
        }
    }

    fun userLogin(email: String, hashedPassword: String) {
        viewModelScope.launch {
            _loginUser.postValue(userRepository.userLogin(email, hashedPassword))
        }
    }

    fun getUserByEmail(email:String)
    {
        viewModelScope.launch {
            _userByEmail.postValue(userRepository.getUserByEmail(email))
        }
    }

}