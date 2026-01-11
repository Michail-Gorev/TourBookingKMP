package org.example.tourbookingkmp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.tourbookingkmp.domain.models.TourDetails
import org.example.tourbookingkmp.presentation.ui.TourDetailsCard
import org.example.tourbookingkmp.presentation.viewModels.GetTourDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlinx.coroutines.launch
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf

@Composable
fun TourDetailsScreen(
    id: Int,
    viewModel: GetTourDetailsViewModel = koinViewModel{
        parametersOf(id)
    }
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Обработка ошибок в канале
    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { message ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    withDismissAction = true
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            when (val state = uiState) {
                is GetTourDetailsViewModel.UiState.Loading -> {
                    LoadingScreen(message = "Загружаем информацию о выбранном туре")
                }

                is GetTourDetailsViewModel.UiState.Success -> {
                    SuccessScreen(state.data)
                }

                is GetTourDetailsViewModel.UiState.Error -> {
                    ErrorScreen("Информация недоступна")
                }
            }
        }
    }
}

@Composable
fun SuccessScreen(data: TourDetails) {
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TourDetailsCard(tour = data)
    }
}
