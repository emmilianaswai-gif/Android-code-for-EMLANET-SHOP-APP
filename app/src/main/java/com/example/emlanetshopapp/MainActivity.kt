package com.example.emlanetshopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.emlanetshopapp.ui.dashboard.DashboardScreen
import com.example.emlanetshopapp.ui.dashboard.ShopAppState
import com.example.emlanetshopapp.ui.dashboard.ShopDestination
import com.example.emlanetshopapp.ui.dashboard.ShopDestinationScreen
import com.example.emlanetshopapp.ui.theme.EMLANETSHOPAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EMLANETSHOPAPPTheme(darkTheme = true, dynamicColor = false) {
                val state = remember { ShopAppState() }
                var refreshKey by remember { mutableStateOf(0) }
                // State holder is intentionally UI-framework independent; this observable key
                // causes Compose to render after an event mutates the holder.
                fun updateState(block: () -> Unit) { block(); refreshKey++ }
                key(refreshKey) {
                    when (state.destination) {
                        ShopDestination.HOME -> DashboardScreen(
                            state = state,
                            onStateChanged = { updateState(it) },
                            modifier = Modifier.fillMaxSize()
                        )
                        else -> ShopDestinationScreen(
                            state = state,
                            onStateChanged = { updateState(it) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
