package org.example.tourbookingkmp.domain.usecases

import org.example.tourbookingkmp.domain.models.TourDetails
import org.example.tourbookingkmp.domain.interfaces.repositories.TourDetailsRepository

class GetTourDetailsByIdUseCase(
    private val repository: TourDetailsRepository
) {

    suspend fun invoke(tourId: Int): TourDetails {
        val tourDetails: TourDetails = repository.fetchTourDetails(tourId)
        return tourDetails
    }
}