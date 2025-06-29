package org.example.tourbookingkmp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.tourbookingkmp.models.Tour
import org.example.tourbookingkmp.ui.TourCard
import org.example.tourbookingkmp.usecases.SearchTourByCityUseCase
import org.example.tourbookingkmp.viewModels.GetAllToursViewModel

@Composable
fun ToursListScreen(
    viewModel: GetAllToursViewModel,
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is GetAllToursViewModel.UiState.Loading -> LoadingScreen(message = "Загружаем туры для Вас")
        is GetAllToursViewModel.UiState.Success -> SuccessScreen(state.data, navController)
        is GetAllToursViewModel.UiState.Error -> ErrorScreen(state.message)
    }
}


@Composable
fun SuccessScreen(data: List<Tour>, navController: NavHostController) {
    var showContent by remember { mutableStateOf(true) }
    var cityFilter by remember { mutableStateOf("") }

    // Фильтрация данных по городу
    val filteredData = SearchTourByCityUseCase(cityFilter, data).invoke()

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = cityFilter,
            onValueChange = { cityFilter = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Поиск по городу") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )

        Button(onClick = { showContent = !showContent }) {
            Text(if (showContent) "Скрыть туры" else "Показать туры")
        }

        AnimatedVisibility(showContent) {
            if (filteredData.isEmpty()) {
                Text(
                    text = if (cityFilter.isNotEmpty())
                        "Не найдено туров по городу: $cityFilter"
                    else
                        "Информация недоступна",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredData) { tour ->
                        TourCard(tour = tour, navController)
                    }
                }
            }
        }
    }
}