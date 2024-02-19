package com.example.balikitchenclub.network.dto

data class CreateTransactionDto(
    val customer: String,
    val employee: Int,
    val tax: Int,
    val tax_value: Int,
    val total: Int,
    val grand_total: Int,
    val details: List<CreateTransactionDetailDto>
)

data class CreateTransactionDetailDto(
    val menu: Int,
    val price: Int,
    val qty: Int,
    val subtotal: Int
)