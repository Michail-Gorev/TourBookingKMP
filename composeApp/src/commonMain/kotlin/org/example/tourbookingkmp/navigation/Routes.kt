package org.example.tourbookingkmp.navigation

object Routes {
    const val TOURS_LIST = "toursList"
    const val TOUR_DETAILS = "tourDetails"
}

object NavigationArguments {
    const val TOUR_ID = "tourId"
    fun createTourDetailsRoute(tourId: Int) = "${Routes.TOUR_DETAILS}/$tourId"
}