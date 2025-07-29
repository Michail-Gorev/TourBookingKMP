package org.example.tourbookingkmp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.domain.models.User
import org.example.tourbookingkmp.domain.usecases.LoginUserByEmailUseCase

class LoginUserByEmailViewModel(
    private val loginUserByEmailUseCase: LoginUserByEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState: StateFlow<LoginScreenState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<String>(Channel.BUFFERED)
    val eventFlow: Flow<String> = _eventChannel.receiveAsFlow()

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.email, error = null) }
            }
            is LoginScreenEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password, error = null) }
            }
            LoginScreenEvent.LoginClicked -> {
                loginUser()
            }
            LoginScreenEvent.ResetLoginProcess -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loggedInUser = null,
                        loginProcessCompleted = false
                    )
                }
            }
            LoginScreenEvent.ClearError -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    private fun loginUser() {
        val currentEmail = _uiState.value.email
        val currentPassword = _uiState.value.password

        if (currentEmail.isBlank() || currentPassword.isBlank()) {
            _uiState.update { it.copy(error = "Email и пароль не могут быть пустыми.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, loginProcessCompleted = false) }

            try {
                val userData = loginUserByEmailUseCase.invoke(currentEmail, currentPassword)

                if (userData.id == "-1") {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Проверьте корректность введённых данных и попробуйте снова.",
                            loginProcessCompleted = true
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loggedInUser = userData,
                            error = null,
                            loginProcessCompleted = true
                        )
                    }
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Неизвестная ошибка при входе."
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = errorMessage,
                        loginProcessCompleted = true
                    )
                }
            }
        }
    }
    data class LoginScreenState(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val loggedInUser: User? = null,
        val error: String? = null,
        val loginProcessCompleted: Boolean = false
    )

    sealed class LoginScreenEvent {
        data class EmailChanged(val email: String) : LoginScreenEvent()
        data class PasswordChanged(val password: String) : LoginScreenEvent()
        object LoginClicked : LoginScreenEvent()
        object ResetLoginProcess : LoginScreenEvent()
        object ClearError : LoginScreenEvent()
    }

    override fun onCleared() {
        super.onCleared()
        _eventChannel.close()
    }
}
