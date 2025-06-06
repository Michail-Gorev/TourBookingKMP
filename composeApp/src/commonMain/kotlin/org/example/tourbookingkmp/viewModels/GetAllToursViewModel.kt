package org.example.tourbookingkmp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.models.Tour
import org.example.tourbookingkmp.repositories.TourRepository.fetchToursData

class GetAllToursViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = fetchToursData()
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message)
            }
        }
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: List<Tour>) : UiState()
        data class Error(val message: String?) : UiState()
    }
}