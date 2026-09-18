package com.example.emlanetshopapp.data.remote

import com.google.gson.annotations.SerializedName

/** Network representation returned by GET /orders. */
data class OrderResponse(
    @SerializedName(value = "id", alternate = ["orderId", "order_id"])
    val id: String,
    @SerializedName(value = "title", alternate = ["productName", "name"])
    val title: String,
    @SerializedName(value = "subtitle", alternate = ["description"])
    val subtitle: String = "",
    @SerializedName(value = "amount", alternate = ["total", "price"])
    val amount: String,
    val status: String
)
