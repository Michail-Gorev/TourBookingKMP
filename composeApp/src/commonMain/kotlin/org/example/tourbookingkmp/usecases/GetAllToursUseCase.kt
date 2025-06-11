package org.example.tourbookingkmp.usecases

import org.example.tourbookingkmp.models.Tour
import org.example.tourbookingkmp.repositories.TourRepository
import org.example.tourbookingkmp.viewModels.GetAllToursViewModel

suspend fun getAllToursUseCase(
    repository: TourRepository = TourRepository //TODO заменить на DI
): List<Tour> {
    return repository.fetchToursData()
}