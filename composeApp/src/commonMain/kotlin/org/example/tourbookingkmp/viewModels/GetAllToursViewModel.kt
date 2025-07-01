package org.example.tourbookingkmp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okio.IOException
import org.example.tourbookingkmp.models.Tour
import org.example.tourbookingkmp.usecases.GetAllToursUseCase

class GetAllToursViewModel(
    private val useCase: GetAllToursUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // Канал String для обработки ошибок
    private val _errorChannel = Channel<String>()
    val errorEvents = _errorChannel.receiveAsFlow()

    init {
        loadTours()
    }

    private fun loadTours() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = useCase.invoke()
                if (data.isEmpty()) {
                    throw IOException(
                        "Не удалось загрузить туры. Проверьте Интернет-соединение " +
                                "и попробуйте зайти позднее"
                    )
                } else {
                    _uiState.value = UiState.Success(data)
                }
            } catch (e: Exception) {
                _errorChannel.send(e.message ?: "Unknown error occurred")
                _uiState.value = UiState.Success(emptyList())
            }
        }
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: List<Tour>) : UiState()
    }
}