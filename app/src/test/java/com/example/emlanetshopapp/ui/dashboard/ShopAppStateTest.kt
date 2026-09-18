package com.example.emlanetshopapp.ui.dashboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ShopAppStateTest {
    @Test
    fun searchFiltersOrdersCaseInsensitively() {
        val state = ShopAppState()

        state.updateSearch("watch")

        assertEquals(listOf("#8420"), state.visibleOrders.map { it.id })
    }

    @Test
    fun savingAnOrderAddsThenRemovesIt() {
        val state = ShopAppState()

        state.toggleSaved("#8421")
        assertEquals(listOf("#8421"), state.savedOrders.map { it.id })
        state.toggleSaved("#8421")

        assertTrue(state.savedOrders.isEmpty())
    }

    @Test
    fun notificationsAndNavigationUpdateSessionState() {
        val state = ShopAppState()

        state.markNotificationsRead()
        state.navigateTo(ShopDestination.ORDERS)

        assertEquals(0, state.unreadNotifications)
        assertEquals(ShopDestination.ORDERS, state.destination)
        assertFalse(state.visibleOrders.isEmpty())
    }
}
