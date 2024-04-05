package com.rbac.rbacauthenticator

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val authToken: String = "",
)

class AuthViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val snackbarHostState = SnackbarHostState()
    val uiState: StateFlow<AuthUiState> = _uiState


    private val container = AppContainer()
    private val userRepository = container.provideUserRepository()

    fun login(username: String, password: String, navController: NavHostController) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            userRepository.login(username, password)
                .onSuccess { successMessage ->
                navController.navigate(AuthScreen.Token.name) {
                    popUpTo(AuthScreen.Login.name) { inclusive = true }
                }
                snackbarHostState.showSnackbar(successMessage)
            }.onFailure {
                snackbarHostState.showSnackbar(it.message ?: "Ocurrió un error")
            }

            _uiState.value = AuthUiState(isLoading = false)
        }
    }

    fun getAuthToken(code: String) {
        viewModelScope.launch {
            userRepository.getAuthToken(code)
                .onSuccess { token ->
                    _uiState.update {
                        it.copy(authToken = token)
                    }
                }.onFailure {
                    snackbarHostState.showSnackbar(it.message ?: "Ocurrió un error")
                }
        }
    }

    fun tryToRedirectLogin(navController: NavHostController) {
        viewModelScope.launch {
            userRepository.getUser()
                .onSuccess {
                    navController.navigate(AuthScreen.Token.name) {
                        popUpTo(AuthScreen.Login.name) { inclusive = true }
                    }
                }
        }

    }


}