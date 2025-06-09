package org.example.tourbookingkmp.viewModels


import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.models.TourDetails
import org.example.tourbookingkmp.repositories.TourDetailsRepository


class GetTourDetailsViewModel(
    private val repository: TourDetailsRepository
) : StateScreenModel<GetTourDetailsViewModel.State>(State.Loading) {

    sealed class State {
        data object Loading : State()
        data class Success(val data: TourDetails) : State()
        data class Error(val message: String?) : State()
    }
    fun loadData(tourId: Int) {
        screenModelScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Success(data = repository.fetchTourDetails(tourId))
        }
    }


}