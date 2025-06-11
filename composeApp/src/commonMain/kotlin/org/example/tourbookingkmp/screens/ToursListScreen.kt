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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.tourbookingkmp.models.Tour
import org.example.tourbookingkmp.ui.TourCard
import org.example.tourbookingkmp.viewModels.GetAllToursViewModel


class ToursListScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = remember {  GetAllToursViewModel()}
        val uiState = viewModel.state.collectAsState() // Автоматическая подписка

        when (val state = uiState.value) {
            is GetAllToursViewModel.State.Loading -> LoadingScreen(message = "Загружаем туры для Вас")
            is GetAllToursViewModel.State.Success -> SuccessScreen(state.data, navigator)
            is GetAllToursViewModel.State.Error -> ErrorScreen(state.message)
        }
    }
}
@Composable
fun SuccessScreen(data: List<Tour>, navigator: Navigator) {
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
                        TourCard(tour = tour, navigator)
                    }
                }
            }
        }
    }
}