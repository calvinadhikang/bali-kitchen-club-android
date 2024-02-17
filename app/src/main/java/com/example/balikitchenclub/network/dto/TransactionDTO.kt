package com.example.balikitchenclub.network.dto

data class CreateTransactionDto(
    val customer: String,
    val employee: Int,
    val tax: Int,
    val tax_value: Int
)