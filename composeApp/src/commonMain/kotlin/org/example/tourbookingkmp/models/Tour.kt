package org.example.tourbookingkmp.models

data class Tour(
    val id: Comparable<*>, val title: String,
    val description: String,
    val city: String,
    val price: Double,
    val transfer: Boolean,
    val isActive: Boolean) {
}
