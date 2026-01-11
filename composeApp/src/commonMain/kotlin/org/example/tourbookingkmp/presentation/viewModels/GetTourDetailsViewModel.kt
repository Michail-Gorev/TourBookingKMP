package org.example.tourbookingkmp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.domain.models.TourDetails
import org.example.tourbookingkmp.domain.usecases.GetTourDetailsByIdUseCase

class GetTourDetailsViewModel(
    private val tourId: Int,
    private val useCase: GetTourDetailsByIdUseCase
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: TourDetails) : UiState()
        data class Error(val message: String): UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _errorChannel = Channel<String>(Channel.BUFFERED)
    val errorEvents: Flow<String> = _errorChannel.receiveAsFlow()

    init {
        loadTourDetails()
    }

    private fun loadTourDetails() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = useCase.invoke(tourId)
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
                _errorChannel.send(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}
