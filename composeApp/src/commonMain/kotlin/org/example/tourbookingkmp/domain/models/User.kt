package org.example.tourbookingkmp.domain.models

data class User (
    val token: String,
    val id: Comparable<*>,
    val firstName: String,
    val lastName: String,
    val password: String = "",
    val email: String,
    val orders: List<Order>
)