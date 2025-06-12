package org.example.tourbookingkmp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.tourbookingkmp.models.TourDetails
import org.example.tourbookingkmp.ui.TourDetailsCard
import org.example.tourbookingkmp.viewModels.GetTourDetailsViewModel


data class TourDetailsScreen(val tourId: Int): Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { GetTourDetailsViewModel(tourId) }
        val uiState = screenModel.state.collectAsState() // Автоматическая подписка

        when (val state = uiState.value) {
            is GetTourDetailsViewModel.State.Loading -> LoadingScreen(
                message = "Загружаем информацию" +
                        " о выбранном туре"
            )
            is GetTourDetailsViewModel.State.Success -> SuccessScreen(state.data)
            is GetTourDetailsViewModel.State.Error -> ErrorScreen("Network Error")
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
