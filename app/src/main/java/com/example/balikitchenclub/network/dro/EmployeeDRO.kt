package com.example.balikitchenclub.network.dro

data class Employee(
    val id: Int,
    val name: String,
    val username: String,
    val password: String,
    val role: String,
)

data class EmployeeResponseList(
    val error: Boolean,
    val message: String,
    val data: List<Employee>
)

data class EmployeeResponse(
    val error: Boolean,
    val message: String,
    val data: Employee
)