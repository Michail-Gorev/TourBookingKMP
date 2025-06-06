package org.example.tourbookingkmp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.tourbookingkmp.models.Tour
import org.example.tourbookingkmp.ui.TourCard
import org.example.tourbookingkmp.viewModels.GetAllToursViewModel

@Composable
fun ToursListScreen(viewModel: GetAllToursViewModel) {
    val uiState = viewModel.uiState.collectAsState() // Автоматическая подписка
    // Действие на старт экрана. Без него не запустится loadData, без чего
    // не произойдет изменения состояния с Loading на Success (произойдет зависание на
    // LoadingScreen)
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }
    when (val state = uiState.value) {
        is GetAllToursViewModel.UiState.Loading -> LoadingScreen(message = "Загружаем туры для Вас")
        is GetAllToursViewModel.UiState.Success -> SuccessScreen(state.data)
        is GetAllToursViewModel.UiState.Error -> ErrorScreen("Network Error")
    }
}
@Composable
fun SuccessScreen(data: List<Tour>) {
    var showContent by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = { showContent = !showContent }) {
            Text(if (showContent) "Hide tours" else "Show tours")
        }

        AnimatedVisibility(showContent) {
            if (data.isEmpty()) {
                Text("No tours available", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(data) { tour ->
                        TourCard(tour = tour)
                    }
                }
            }
        }
    }
}