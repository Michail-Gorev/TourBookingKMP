package org.example.tourbookingkmp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.models.TourDetails
import org.example.tourbookingkmp.usecases.GetTourDetailsByIdUseCase

class GetTourDetailsViewModel(
    private val tourId: Comparable<*>,
    private val useCase: GetTourDetailsByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = useCase.invoke(tourId)
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: TourDetails) : UiState()
        data class Error(val message: String) : UiState()
    }
}