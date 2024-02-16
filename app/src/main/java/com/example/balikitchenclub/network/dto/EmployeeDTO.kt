package com.example.balikitchenclub.network.dto

data class CreateEmployeeDto(
    val name: String,
    val username: String,
    val password: String,
    val role: String
)