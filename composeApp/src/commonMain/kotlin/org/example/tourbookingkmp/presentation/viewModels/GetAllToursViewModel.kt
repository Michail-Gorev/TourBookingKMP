package org.example.tourbookingkmp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.IOException
import org.example.tourbookingkmp.domain.models.Tour
import org.example.tourbookingkmp.domain.usecases.GetAllToursUseCase
import org.example.tourbookingkmp.domain.usecases.SearchTourByCityUseCase

class GetAllToursViewModel(
    private val getAllToursUseCase: GetAllToursUseCase,
    private val searchTourByCityUseCase: SearchTourByCityUseCase
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val data: List<Tour>) : UiState()
        data class Error(val message: String): UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // Канал для ошибок
    private val _errorChannel = Channel<String>(Channel.BUFFERED)
    val errorEvents = _errorChannel.receiveAsFlow()

    private val _cityFilter = MutableStateFlow("")
    val cityFilter: StateFlow<String> = _cityFilter.asStateFlow()

    val filteredTours: StateFlow<List<Tour>> = combine(_uiState, _cityFilter) { state, filter ->
        when (state) {
            is UiState.Success -> searchTourByCityUseCase.invoke(filter, state.data)
            else -> emptyList()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadTours()
    }

    fun updateCityFilter(newValue: String) {
        _cityFilter.value = newValue
    }

    private fun loadTours() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = getAllToursUseCase.invoke()
                if (data.isEmpty()) {
                    throw IOException("Не удалось загрузить туры. Проверьте Интернет-соединение и попробуйте позднее.")
                }
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                val msg = e.message ?: "Неизвестная ошибка"
                _errorChannel.send(msg)
                _uiState.value = UiState.Error(msg)
            }
        }
    }
}
