package org.example.tourbookingkmp.repositories

import org.example.tourbookingkmp.GetTourDetailsByTourIdQuery
import org.example.tourbookingkmp.apolloClient
import org.example.tourbookingkmp.models.Museum
import org.example.tourbookingkmp.models.TourDetails

class TourDetailsRepository {
    suspend fun fetchTourDetails(tourId: Int): TourDetails {
        val response = apolloClient.query(GetTourDetailsByTourIdQuery(tourId.toString())).execute()
        val museums: List<Museum> = response.data?.tour?.museums?.map {
            Museum (
                name = it.name,
                city = it.city
            )
        } ?: emptyList()
        val tourDetails: TourDetails = response.data?.tour.let { tour ->
            tour?.let {
                TourDetails(
                    title = it.title,
                    description = it.description,
                    isActive = it.isActive,
                    museums = museums
                )
            }!!
        }
       return tourDetails
    }
}