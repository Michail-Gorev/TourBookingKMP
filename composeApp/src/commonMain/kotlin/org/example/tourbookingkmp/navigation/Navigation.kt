package org.example.tourbookingkmp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.tourbookingkmp.domain.interfaces.repositories.UserTokenRepository
import org.example.tourbookingkmp.presentation.screens.LoginScreen
import org.example.tourbookingkmp.presentation.screens.RegistrationScreen
import org.example.tourbookingkmp.presentation.screens.TourDetailsScreen
import org.example.tourbookingkmp.presentation.screens.ToursListScreen
import org.example.tourbookingkmp.presentation.viewModels.GetAllToursViewModel
import org.example.tourbookingkmp.presentation.viewModels.GetTourDetailsViewModel
import org.example.tourbookingkmp.presentation.viewModels.LoginUserByEmailViewModel
import org.example.tourbookingkmp.presentation.viewModels.RegisterUserByEmailViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val userTokenRepository = koinInject<UserTokenRepository>()
    val token = userTokenRepository.readUserTokenFromFile()

    NavHost(
        navController = navController,
        startDestination = if (token.isNotBlank()) {
            Routes.TOURS_LIST
        } else Routes.LOGIN
    ) {
        composable(Routes.TOURS_LIST) {
            val viewModel: GetAllToursViewModel = koinViewModel()
            ToursListScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            Routes.TOUR_DETAILS,
            arguments = listOf(navArgument("tourId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tourId = backStackEntry.savedStateHandle.get<Int>("tourId")
            val viewModel: GetTourDetailsViewModel =
                koinViewModel(parameters = { parametersOf(tourId) })
            TourDetailsScreen(viewModel = viewModel)
        }
        composable(Routes.LOGIN) {
            val viewModel: LoginUserByEmailViewModel =
                koinViewModel()
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable(Routes.REGISTER) {
            val viewModel: RegisterUserByEmailViewModel =
                koinViewModel()
            RegistrationScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}