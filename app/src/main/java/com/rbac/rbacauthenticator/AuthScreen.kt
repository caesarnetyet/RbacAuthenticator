package com.rbac.rbacauthenticator
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class AuthScreen {
    Home,
    Login,
    Token
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTopBar() {
    TopAppBar(
        title = { Text("RBAC Authentication App") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )

}

@Composable
fun AuthApp(
    navController: NavHostController = rememberNavController(),
    viewModel: AuthViewModel = AuthViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.tryToRedirectLogin(navController)

    Scaffold(
        topBar = { AuthTopBar() },
        snackbarHost = { SnackbarHost(hostState = viewModel.snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AuthScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AuthScreen.Login.name) {
                LoginScreen(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    isLoading = uiState.isLoading,
                    onLogin = { email, password -> viewModel.login(email, password, navController) }
                )
            }
            composable(route = AuthScreen.Token.name) {
                TokenManagementScreen (
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    token = uiState.authToken,
                    onGetToken = { viewModel.getAuthToken(it)}
                )
            }
        }
    }

}