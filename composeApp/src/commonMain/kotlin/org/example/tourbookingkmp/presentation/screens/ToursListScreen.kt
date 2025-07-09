package org.example.tourbookingkmp.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.domain.models.Tour
import org.example.tourbookingkmp.presentation.ui.TourCard
import org.example.tourbookingkmp.presentation.viewModels.GetAllToursViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ToursListScreen(
    viewModel: GetAllToursViewModel = koinViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val cityFilter by viewModel.cityFilter.collectAsState()
    val filteredTours by viewModel.filteredTours.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Обработка ошибок в канале
    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = error,
                    withDismissAction = true
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (uiState) {
                is GetAllToursViewModel.UiState.Loading -> {
                    LoadingScreen(message = "Загружаем туры для Вас")
                }

                is GetAllToursViewModel.UiState.Success -> {
                    SuccessScreen(
                        cityFilter = cityFilter,
                        onCityFilterChange = viewModel::updateCityFilter,
                        tours = filteredTours,
                        navController = navController
                    )
                }
                is GetAllToursViewModel.UiState.Error -> {
                    ErrorScreen(
                        (uiState as GetAllToursViewModel.UiState.Error).message
                    )
                }
            }
        }
    }
}


@Composable
private fun SuccessScreen(
    cityFilter: String,
    onCityFilterChange: (String) -> Unit,
    tours: List<Tour>,
    navController: NavHostController
) {
    var showContent by rememberSaveable { mutableStateOf(true) }

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
            onValueChange = onCityFilterChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Поиск по городу") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )

        Button(onClick = { showContent = !showContent }) {
            Text(if (showContent) "Скрыть туры" else "Показать туры")
        }

        AnimatedVisibility(showContent) {
            if (tours.isEmpty()) {
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
                    items(tours) { tour ->
                        TourCard(tour = tour, navController)
                    }
                }
            }
        }
    }
}
