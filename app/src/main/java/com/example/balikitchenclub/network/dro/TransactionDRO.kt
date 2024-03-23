package com.example.balikitchenclub.network.dro

data class TransactionListDro(
    val data: List<TransactionResponseItem>,
    val total_earning: Int,
    val grand_total: Int,
)

data class TransactionResponseItem(
    val id: Int,
    val customer: String,
    val employee: Int,
    val tax: Int,
    val tax_value: Int,
    val total: Int,
    val grand_total: Int,
    val sesi: Int,
    var status: String,
    val createdAt: String,
    val details: List<DetailTransactionResponseItem>,
    val employee_detail: EmployeeResponseItem
)

data class DetailTransactionResponseItem(
    val id: Int,
    val menu: Int,
    val price: Int,
    val name: String,
    val qty: Int,
    val subtotal: Int
)