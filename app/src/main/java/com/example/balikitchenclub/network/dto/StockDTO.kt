package com.example.balikitchenclub.network.dto

data class CreateStockDto(
    val newQuantity: Int,
    val userId: Int,
)