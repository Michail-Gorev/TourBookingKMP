package org.example.tourbookingkmp.usecases

import org.example.tourbookingkmp.models.TourDetails
import org.example.tourbookingkmp.repositories.TourDetailsRepository

class GetTourDetailsByIdUseCase(
    private val repository: TourDetailsRepository) {

    suspend fun invoke(tourId: Comparable<*>): TourDetails {
        val tourDetails: TourDetails = repository.fetchTourDetails(tourId.toString().toInt())
        return tourDetails
    }
}