package com.example.balikitchenclub.network.dro

data class StockDro(
    val id: Int,
    val menu: Int,
    val qty: Int,
    val reference: Int,
    val status: String,
    val type: String,
    val createdAt: String
)