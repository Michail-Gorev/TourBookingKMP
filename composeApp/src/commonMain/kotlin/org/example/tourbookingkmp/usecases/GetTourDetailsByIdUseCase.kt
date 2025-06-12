package org.example.tourbookingkmp.usecases

import org.example.tourbookingkmp.models.TourDetails
import org.example.tourbookingkmp.repositories.TourDetailsRepository

class GetTourDetailsByIdUseCase(
    private val repository: TourDetailsRepository = TourDetailsRepository, //TODO заменить на DI
    private val tourId: Comparable<*>) {

    suspend fun invoke(): TourDetails {
        val tourId = tourId.toString().toInt()
        val tourDetails: TourDetails = repository.fetchTourDetails(tourId)
        return tourDetails
    }
}