package org.example.tourbookingkmp.navigation.nav3

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {


    @Serializable
    data object TourList: Route, NavKey

    @Serializable
    data class TourDetails(val id: Int): Route, NavKey
}