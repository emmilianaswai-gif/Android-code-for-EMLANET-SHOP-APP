package com.example.emlanetshopapp.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/** Destinations exposed by the shop's primary navigation. */
enum class ShopDestination(val label: String) {
    HOME("Home"), ORDERS("Orders"), SAVED("Saved"), PROFILE("Profile"), NOTIFICATIONS("Notifications")
}

/**
 * In-memory state holder for the current session. Keeping state updates here makes the UI
 * deterministic now and gives a single integration point for a future repository/API.
 */
class ShopAppState(initialOrders: List<OrderUiModel> = sampleOrders()) {
    var destination by mutableStateOf(ShopDestination.HOME)
        private set
    var searchQuery by mutableStateOf("")
        private set
    var savedOrderIds by mutableStateOf(emptySet<String>())
        private set
    var notificationsRead by mutableStateOf(false)
        private set

    var orders by mutableStateOf(initialOrders)
        private set
    val visibleOrders: List<OrderUiModel>
        get() = orders.filter { order ->
            searchQuery.isBlank() || listOf(order.id, order.title, order.subtitle, order.status)
                .any { it.contains(searchQuery, ignoreCase = true) }
        }
    val savedOrders: List<OrderUiModel>
        get() = orders.filter { it.id in savedOrderIds }
    val unreadNotifications: Int
        get() = if (notificationsRead) 0 else 3

    fun navigateTo(value: ShopDestination) { destination = value }
    fun updateSearch(value: String) { searchQuery = value }
    fun toggleSaved(orderId: String) {
        savedOrderIds = if (orderId in savedOrderIds) savedOrderIds - orderId else savedOrderIds + orderId
    }
    fun markNotificationsRead() { notificationsRead = true }
    fun replaceOrders(value: List<OrderUiModel>) {
        orders = value
        savedOrderIds = savedOrderIds.intersect(value.map { it.id }.toSet())
    }
}
