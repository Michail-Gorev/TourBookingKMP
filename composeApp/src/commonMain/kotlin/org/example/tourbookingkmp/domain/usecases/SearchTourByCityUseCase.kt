package org.example.tourbookingkmp.domain.usecases

import org.example.tourbookingkmp.domain.models.Tour


class SearchTourByCityUseCase {

    fun invoke(cityName: String, toursList: List<Tour>): List<Tour> {

        if (cityName.isBlank()) {
            return toursList
        } else {
            val filteredList = toursList.filter {
                it.city.lowercase().contains(cityName.lowercase())
            }
            return filteredList
        }
    }
}