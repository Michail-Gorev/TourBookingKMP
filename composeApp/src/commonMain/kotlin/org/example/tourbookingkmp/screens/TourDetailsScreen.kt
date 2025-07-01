package org.example.tourbookingkmp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.tourbookingkmp.models.TourDetails
import org.example.tourbookingkmp.ui.TourDetailsCard
import org.example.tourbookingkmp.viewModels.GetTourDetailsViewModel

@Composable
fun TourDetailsScreen(
    viewModel: GetTourDetailsViewModel
) {
    val rememberedViewModel = remember { viewModel }
    val uiState = rememberedViewModel.uiState.collectAsState() // Автоматическая подписка

    when (val state = uiState.value) {
        is GetTourDetailsViewModel.UiState.Loading -> LoadingScreen(
            message = "Загружаем информацию" +
                    " о выбранном туре"
        )

        is GetTourDetailsViewModel.UiState.Success -> SuccessScreen(state.data)
        is GetTourDetailsViewModel.UiState.Error -> ErrorScreen("Network Error")
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
