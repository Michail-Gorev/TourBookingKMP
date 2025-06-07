package org.example.tourbookingkmp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.models.TourDetails
import org.example.tourbookingkmp.repositories.TourDetailsRepository.fetchTourDetails

class GetTourDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadData(tourId: Int) {
        // Если уже в состоянии Success, не загружаем снова
        if (_uiState.value is UiState.Success) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = fetchTourDetails(tourId)
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message)
            }
        }
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: TourDetails) : UiState()
        data class Error(val message: String?) : UiState()
    }
}