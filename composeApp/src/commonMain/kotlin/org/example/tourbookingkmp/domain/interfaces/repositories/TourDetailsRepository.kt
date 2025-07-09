package org.example.tourbookingkmp.domain.interfaces.repositories

import org.example.tourbookingkmp.domain.models.TourDetails

interface TourDetailsRepository {
    suspend fun fetchTourDetails(tourId: Int): TourDetails
}