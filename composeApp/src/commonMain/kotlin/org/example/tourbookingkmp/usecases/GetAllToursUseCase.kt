package org.example.tourbookingkmp.usecases

import org.example.tourbookingkmp.GetAllToursQuery
import org.example.tourbookingkmp.apolloClient
import org.example.tourbookingkmp.models.Tour

suspend fun getAllTours(): List<Tour> {
    val response = apolloClient.query(GetAllToursQuery()).execute()
    return response.data?.tours?.map {
        Tour(
            id = it.id ?: 1,
            title = it.title ?: "Untitled",
            price = it.price ?: 0.0,
            city = it.city ?: "Unknown",
            description = "TODO()",
            transfer = true,
            isActive = true
        )
    } ?: emptyList()
}