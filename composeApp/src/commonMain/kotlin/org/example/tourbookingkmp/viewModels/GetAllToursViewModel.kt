package org.example.tourbookingkmp.viewModels

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.models.Tour
import org.example.tourbookingkmp.repositories.TourRepository.fetchToursData

class GetAllToursViewModel: StateScreenModel<GetAllToursViewModel.State>(State.Loading) {

    sealed class State {
        data object Loading : State()
        data class Success(val data: List<Tour>) : State()
        data class Error(val message: String?) : State()
    }

    fun loadData() {
        screenModelScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Success(fetchToursData())
        }
    }
}