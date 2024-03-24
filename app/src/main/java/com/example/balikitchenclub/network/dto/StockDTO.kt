package com.example.balikitchenclub.network.dto

data class CreateStockDto(
    val menu: Int,
    val qty: Int,
    val employee: Int,
)