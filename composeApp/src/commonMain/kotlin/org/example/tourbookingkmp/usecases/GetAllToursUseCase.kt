package org.example.tourbookingkmp.usecases

import org.example.tourbookingkmp.models.Tour
import org.example.tourbookingkmp.repositories.TourRepository

class GetAllToursUseCase(
    private val repository: TourRepository
) {
    suspend fun invoke(): List<Tour> {
        val toursList = repository.fetchToursData()
        return toursList
    }
}