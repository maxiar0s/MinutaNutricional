package com.exp1_s1.minutanutricional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.exp1_s1.minutanutricional.ui.minuta.MinutaScreen
import com.exp1_s1.minutanutricional.ui.theme.MinutaNutricionalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinutaNutricionalTheme {
                MinutaScreen()
            }
        }
    }
}
