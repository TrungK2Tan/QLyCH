package com.example.quanlych.model


data class Product(
    val imageResId: Int,
    val name: String,
    val price: String,
    var quantity: Int,
    var isSelected: Boolean = false
)
