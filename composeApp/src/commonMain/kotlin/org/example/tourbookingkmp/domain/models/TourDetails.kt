package org.example.tourbookingkmp.domain.models

data class TourDetails(
    val title: String,
    val description: String,
    val isActive: Boolean,
    val museums: List<Museum>
)
