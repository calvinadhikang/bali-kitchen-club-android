package com.example.balikitchenclub.network.dto

data class CreateMenuDto(
    val name: String,
    val price: Int,
    val category: String,
)

data class UpdateMenuDto(
    val name: String,
    val price: Int,
)