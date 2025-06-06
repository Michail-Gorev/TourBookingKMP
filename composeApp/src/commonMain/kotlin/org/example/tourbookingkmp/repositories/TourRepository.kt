package org.example.tourbookingkmp.repositories

import org.example.tourbookingkmp.models.Tour
import org.example.tourbookingkmp.usecases.getAllTours

object TourRepository {
    suspend fun fetchToursData(): List<Tour> {
        return getAllTours()
    }
}
