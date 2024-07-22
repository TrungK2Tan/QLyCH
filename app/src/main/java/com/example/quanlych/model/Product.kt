package com.example.quanlych.model


data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageResource: Int,
    val price: Double,
    var quantity: Int,
    var isSelected: Boolean = false
)
