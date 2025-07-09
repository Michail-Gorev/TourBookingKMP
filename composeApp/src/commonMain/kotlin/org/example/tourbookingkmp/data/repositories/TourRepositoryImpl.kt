package org.example.tourbookingkmp.data.repositories

import org.example.tourbookingkmp.GetAllToursQuery
import org.example.tourbookingkmp.data.apolloClient
import org.example.tourbookingkmp.domain.interfaces.repositories.TourRepository
import org.example.tourbookingkmp.domain.models.Tour

class TourRepositoryImpl: TourRepository {

    override suspend fun fetchToursData(): List<Tour> {
        val response = apolloClient.query(GetAllToursQuery()).execute()
        return response.data?.tours?.map {
            Tour(
                id = it.id,
                title = it.title,
                price = it.price,
                city = it.city,
                description = "TODO()",
                transfer = true,
                isActive = true
            )
        } ?: emptyList()
    }
}
