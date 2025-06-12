package org.example.tourbookingkmp.viewModels


import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.models.TourDetails
import org.example.tourbookingkmp.usecases.GetTourDetailsByIdUseCase


class GetTourDetailsViewModel(
    private val tourId: Comparable<*>,
    private val getTourDetailsByIdUseCase: GetTourDetailsByIdUseCase = GetTourDetailsByIdUseCase(
        tourId = tourId
    ),
) : StateScreenModel<GetTourDetailsViewModel.State>(State.Loading) {

    init {
        screenModelScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Success(
                data =
                    getTourDetailsByIdUseCase.invoke()
            )
        }
    }

    sealed class State {
        data object Loading : State()
        data class Success(val data: TourDetails) : State()
        data class Error(val message: String?) : State()
    }
}