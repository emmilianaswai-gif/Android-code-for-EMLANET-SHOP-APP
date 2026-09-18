package com.example.emlanetshopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.emlanetshopapp.ui.dashboard.DashboardScreen
import com.example.emlanetshopapp.ui.theme.EMLANETSHOPAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EMLANETSHOPAPPTheme(darkTheme = true, dynamicColor = false) {
                DashboardScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}