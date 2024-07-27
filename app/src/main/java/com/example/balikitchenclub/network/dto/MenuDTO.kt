package com.example.balikitchenclub.network.dto

data class CreateMenuDto(
    val name: String,
    val price: Int,
    val categoryId: Int,
)

data class UpdateMenuDto(
    val name: String,
    val price: Int,
)