package org.example.tourbookingkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.tourbookingkmp.navigation.NavigationArguments
import org.example.tourbookingkmp.navigation.Routes
import org.example.tourbookingkmp.screens.TourDetailsScreen
import org.example.tourbookingkmp.screens.ToursListScreen
import org.example.tourbookingkmp.viewModels.GetAllToursViewModel
import org.example.tourbookingkmp.viewModels.GetTourDetailsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavigation()
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.TOURS_LIST
    ) {
        composable(Routes.TOURS_LIST) {
            ToursListScreen(
                navController = navController,
                viewModel = GetAllToursViewModel()
            )
        }
        composable(
            route = "${Routes.TOUR_DETAILS}/{${NavigationArguments.TOUR_ID}}",
            arguments = listOf(
                navArgument(NavigationArguments.TOUR_ID) {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
            val tourId = navController.currentBackStackEntry?.savedStateHandle?.get<Int>("tourId")!!
            val viewModel = remember(tourId) {GetTourDetailsViewModel()}
            TourDetailsScreen(
                tourId = tourId,
                viewModel = viewModel
            )
        }
    }
}