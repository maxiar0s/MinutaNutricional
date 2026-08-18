package com.exp1_s1.minutanutricional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.exp1_s1.minutanutricional.ui.access.LoginScreen
import com.exp1_s1.minutanutricional.ui.access.RecoveryScreen
import com.exp1_s1.minutanutricional.ui.access.RegistrationScreen
import com.exp1_s1.minutanutricional.ui.minuta.MinutaScreen
import com.exp1_s1.minutanutricional.ui.theme.MinutaNutricionalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinutaNutricionalTheme {
                MinutaNutricionalApp()
            }
        }
    }
}

private enum class AppScreen {
    Login,
    Registration,
    Recovery,
    WeeklyMenu
}

@Composable
private fun MinutaNutricionalApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.Login) }

    when (currentScreen) {
        AppScreen.Login -> LoginScreen(
            onLogin = { currentScreen = AppScreen.WeeklyMenu },
            onRegister = { currentScreen = AppScreen.Registration },
            onRecoverPassword = { currentScreen = AppScreen.Recovery }
        )
        AppScreen.Registration -> RegistrationScreen(onBackToLogin = { currentScreen = AppScreen.Login })
        AppScreen.Recovery -> RecoveryScreen(onBackToLogin = { currentScreen = AppScreen.Login })
        AppScreen.WeeklyMenu -> MinutaScreen(onLogOut = { currentScreen = AppScreen.Login })
    }
}
