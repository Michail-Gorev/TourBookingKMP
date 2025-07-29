package org.example.tourbookingkmp.domain.models

data class Order(
    val id: Comparable<*>,
    val cost: Float = 0.toFloat(),
    val tour: Tour = Tour(
        "",
        "",
        "",
        "",
        0.0,
        transfer = true,
        isActive = true
    ),
    val user: User = User(
        "",
        "",
        "",
        "",
        "",
        "",
        emptyList()
    ),
    val isConfirmed: Boolean = false
)
