package org.example.tourbookingkmp.domain.usecases

import org.example.tourbookingkmp.domain.models.Tour
import org.example.tourbookingkmp.domain.interfaces.repositories.TourRepository

class GetAllToursUseCase(
    private val repository: TourRepository
) {
    suspend fun invoke(): List<Tour> {
        val toursList = repository.fetchToursData()
        return toursList
    }
}