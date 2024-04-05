package com.rbac.rbacauthenticator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rbac.rbacauthenticator.ui.theme.RbacAuthenticatorTheme

class MainActivity : ComponentActivity() {
    private val container = AppContainer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RbacAuthenticatorTheme {
                // A surface container using the 'background' color from the theme
                AuthApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RbacAuthenticatorTheme {
        Greeting("Android")
    }
}