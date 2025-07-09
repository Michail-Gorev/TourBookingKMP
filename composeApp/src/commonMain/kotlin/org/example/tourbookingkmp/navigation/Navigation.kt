package org.example.tourbookingkmp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.tourbookingkmp.presentation.screens.TourDetailsScreen
import org.example.tourbookingkmp.presentation.screens.ToursListScreen
import org.example.tourbookingkmp.presentation.viewModels.GetAllToursViewModel
import org.example.tourbookingkmp.presentation.viewModels.GetTourDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "tours_list"
    ) {
        composable("tours_list") {
            val viewModel: GetAllToursViewModel = koinViewModel()
            ToursListScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            "tour_details/{tourId}",
            arguments = listOf(navArgument("tourId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tourId = backStackEntry.savedStateHandle.get<Int>("tourId")
            val viewModel: GetTourDetailsViewModel =
                koinViewModel(parameters = { parametersOf(tourId) })
            TourDetailsScreen(viewModel = viewModel)
        }
    }
}