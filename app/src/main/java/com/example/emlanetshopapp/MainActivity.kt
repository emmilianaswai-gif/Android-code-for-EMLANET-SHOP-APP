package com.example.emlanetshopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emlanetshopapp.ui.dashboard.DashboardScreen
import com.example.emlanetshopapp.ui.dashboard.ShopAppState
import com.example.emlanetshopapp.ui.dashboard.ShopDestination
import com.example.emlanetshopapp.ui.dashboard.ShopDestinationScreen
import com.example.emlanetshopapp.ui.dashboard.ShopViewModel
import com.example.emlanetshopapp.ui.theme.EMLANETSHOPAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EMLANETSHOPAPPTheme(darkTheme = true, dynamicColor = false) {
                val viewModel: ShopViewModel = viewModel()
                val networkState by viewModel.uiState.collectAsStateWithLifecycle()
                val state = remember { ShopAppState() }
                LaunchedEffect(networkState.orders) {
                    networkState.orders?.let(state::replaceOrders)
                }
                run {
                    when (state.destination) {
                        ShopDestination.HOME -> DashboardScreen(
                            state = state,
                            onStateChanged = { it() },
                            modifier = Modifier.fillMaxSize()
                        )
                        else -> ShopDestinationScreen(
                            state = state,
                            onStateChanged = { it() },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
