package com.example.balikitchenclub.network.dro

data class AuthDro (
    val error: Boolean,
    val message: String,
    val data: Employee?
)