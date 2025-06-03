package org.example.tourbookingkmp.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.domain.TourRepository
import org.example.tourbookingkmp.models.Tour

class TourViewModel : ViewModel() {

    private val _tours = MutableStateFlow<List<Tour>>(emptyList())
    val tours: StateFlow<List<Tour>> = _tours.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchTours()
    }

    private fun fetchTours() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = TourRepository.getAllTours()
                _tours.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load tours: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
