package com.rbac.app.viewmodel

import androidx.lifecycle.ViewModel
import com.rbac.data.repositories.IUserRepository

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val onLogin: () -> Unit,
    val isUserLoggedIn: Boolean = false
)

class LoginViewModel(
    private val userRepository: IUserRepository
): ViewModel() {



    fun login(email: String, password: String) {


    }


}