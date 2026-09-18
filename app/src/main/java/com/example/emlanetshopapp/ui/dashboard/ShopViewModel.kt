package com.example.emlanetshopapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emlanetshopapp.data.remote.OrderResponse
import com.example.emlanetshopapp.data.remote.RetrofitClient
import com.example.emlanetshopapp.ui.theme.DashboardCyan
import com.example.emlanetshopapp.ui.theme.DashboardSuccess
import com.example.emlanetshopapp.ui.theme.DashboardWarning
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color

data class ShopNetworkUiState(
    val isLoading: Boolean = false,
    val orders: List<OrderUiModel>? = null,
    val errorMessage: String? = null
)

class ShopViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ShopNetworkUiState(isLoading = true))
    val uiState: StateFlow<ShopNetworkUiState> = _uiState.asStateFlow()

    init { refreshOrders() }

    fun refreshOrders() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            runCatching { RetrofitClient.apiService.getOrders().map(OrderResponse::toUiModel) }
                .onSuccess { _uiState.value = ShopNetworkUiState(orders = it) }
                .onFailure { error ->
                    // The dashboard keeps its local sample data visible when the dev API is unavailable.
                    _uiState.value = ShopNetworkUiState(errorMessage = error.message ?: "Unable to load orders")
                }
        }
    }
}

private fun OrderResponse.toUiModel(): OrderUiModel {
    val normalizedStatus = status.ifBlank { "Pending" }
    val (container, content) = when (normalizedStatus.lowercase()) {
        "delivered" -> DashboardSuccess.copy(alpha = .15f) to Color(0xFF4ADE80)
        "shipped" -> DashboardCyan.copy(alpha = .15f) to Color(0xFF67E8F9)
        else -> DashboardWarning.copy(alpha = .15f) to Color(0xFFFCD34D)
    }
    return OrderUiModel(id, title, subtitle, amount, normalizedStatus, container, content)
}
