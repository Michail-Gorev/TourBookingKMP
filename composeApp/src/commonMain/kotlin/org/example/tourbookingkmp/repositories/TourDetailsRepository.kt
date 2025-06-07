package org.example.tourbookingkmp.repositories

import org.example.tourbookingkmp.models.TourDetails
import org.example.tourbookingkmp.usecases.getTourDetailsById

object TourDetailsRepository {
    suspend fun fetchTourDetails(tourId: Int): TourDetails {
       return getTourDetailsById(tourId)
    }
}