package com.example.emlanetshopapp.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("orders")
    suspend fun getOrders(): List<OrderResponse>
}
