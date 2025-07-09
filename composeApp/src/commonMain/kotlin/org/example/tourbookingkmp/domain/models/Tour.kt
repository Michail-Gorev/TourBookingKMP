package org.example.tourbookingkmp.domain.models

data class Tour(
    val id: Comparable<*>, val title: String,
    val description: String,
    val city: String,
    val price: Double,
    val transfer: Boolean,
    val isActive: Boolean)