package com.example.quanlych.model
data class Order(
    val id: Int,
    val userId: Int,
    val date: String,
    val total: Double,
    val status: Int,
    val address: String,
    val phone: String,
    val paymentMethodId: Int
)
