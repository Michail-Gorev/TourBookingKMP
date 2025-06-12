package org.example.tourbookingkmp.usecases

import org.example.tourbookingkmp.models.Tour


class SearchTourByCityUseCase(
    private val cityName: String,
    private val toursList: List<Tour>
) {

    fun invoke(): List<Tour> {

        if (cityName.isBlank()) {
            return toursList
        }
        else {
            val filteredList = toursList.filter {
                it.city.lowercase().contains(cityName.lowercase())
            }
            return filteredList
        }
    }
}