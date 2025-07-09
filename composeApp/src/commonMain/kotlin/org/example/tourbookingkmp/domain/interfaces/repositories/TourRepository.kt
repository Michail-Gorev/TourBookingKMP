package org.example.tourbookingkmp.domain.interfaces.repositories

import org.example.tourbookingkmp.domain.models.Tour

interface TourRepository {
    suspend fun fetchToursData(): List<Tour>
}