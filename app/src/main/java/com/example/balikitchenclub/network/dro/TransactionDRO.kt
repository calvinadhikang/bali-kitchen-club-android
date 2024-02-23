package com.example.balikitchenclub.network.dro

data class TransactionResponseItem(
    val id: Int,
    val customer: String,
    val employee: Int,
    val tax: Int,
    val tax_value: Int,
    val total: Int,
    val grand_total: Int,
    val sesi: Int,
    val status: String,
    val createdAt: String,
    val details: List<DetailTransactionResponseItem>
)

data class DetailTransactionResponseItem(
    val id: Int,
    val menu: Int,
    val price: Int,
    val qty: Int,
    val subtotal: Int
)