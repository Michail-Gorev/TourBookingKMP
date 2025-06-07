package org.example.tourbookingkmp.models

data class TourDetails(
    val title: String,
    val description: String,
    val isActive: Boolean,
    val museums: List<Museum>
)
