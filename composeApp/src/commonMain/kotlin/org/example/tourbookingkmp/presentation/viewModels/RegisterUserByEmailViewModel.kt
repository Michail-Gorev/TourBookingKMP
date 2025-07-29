package org.example.tourbookingkmp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.domain.models.User
import org.example.tourbookingkmp.domain.usecases.RegisterUserByEmailUseCase
import kotlin.time.ExperimentalTime


class RegisterUserByEmailViewModel(
    private val registerUserByEmailUseCase: RegisterUserByEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationScreenState())
    val uiState: StateFlow<RegistrationScreenState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<String>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.FirstNameChanged ->
                _uiState.update { it.copy(firstName = event.firstName, registrationError = null) }

            is RegistrationEvent.LastNameChanged ->
                _uiState.update { it.copy(lastName = event.lastName, registrationError = null) }

            is RegistrationEvent.EmailChanged ->
                _uiState.update { it.copy(email = event.email, registrationError = null) }

            is RegistrationEvent.PasswordChanged ->
                _uiState.update { it.copy(password = event.password, registrationError = null) }

            is RegistrationEvent.ConfirmPasswordChanged ->
                _uiState.update {
                    it.copy(
                        confirmPassword = event.confirmPassword,
                        registrationError = null
                    )
                }

            RegistrationEvent.RegisterClicked ->
                performRegistration()

            RegistrationEvent.RegistrationProcessCompleted ->
                _uiState.update {
                    it.copy(
                        registrationSuccess = false,
                        navigateToLogin = false,
                        registrationError = null
                    )
                }

            RegistrationEvent.ErrorSnackbarDismissed ->
                _uiState.update { it.copy(registrationError = null) }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun performRegistration() {
        val currentState = _uiState.value
        val simpleEmailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

        if (currentState.firstName.isBlank() || currentState.lastName.isBlank() || currentState.email.isBlank() ||
            currentState.password.isBlank() || currentState.confirmPassword.isBlank()
        ) {
            _uiState.update { it.copy(registrationError = "Все поля обязательны для заполнения.") }
            return
        }

        if (!currentState.email.matches(simpleEmailRegex)) {
            _uiState.update { it.copy(registrationError = "Введите корректный email.") }
            return
        }

        if (currentState.password.length < 6) {
            _uiState.update { it.copy(registrationError = "Пароль должен содержать не менее 6 символов.") }
            return
        }

        if (currentState.password != currentState.confirmPassword) {
            _uiState.update { it.copy(registrationError = "Пароли не совпадают.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, registrationError = null) }
            try {
                val userToRegister = User(
                    token = "",
                    id = currentState.email,
                    firstName = currentState.firstName,
                    lastName = currentState.lastName,
                    email = currentState.email,
                    password = currentState.password,
                    orders = emptyList()
                )

                val registrationToken = registerUserByEmailUseCase.invoke(userToRegister)
                if (registrationToken.isNotBlank()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registrationSuccess = true,
                            navigateToLogin = true
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registrationError = "Регистрация не удалась. Email может быть уже занят."
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        registrationError = e.message ?: "Произошла ошибка регистрации."
                    )
                }
            }
        }
    }

    data class RegistrationScreenState(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val isLoading: Boolean = false,
        val registrationError: String? = null,
        val registrationSuccess: Boolean = false,
        val navigateToLogin: Boolean = false
    )

    sealed class RegistrationEvent {
        data class FirstNameChanged(val firstName: String) : RegistrationEvent() // Updated
        data class LastNameChanged(val lastName: String) : RegistrationEvent()   // Updated
        data class EmailChanged(val email: String) : RegistrationEvent()
        data class PasswordChanged(val password: String) : RegistrationEvent()
        data class ConfirmPasswordChanged(val confirmPassword: String) : RegistrationEvent()
        object RegisterClicked : RegistrationEvent()
        object RegistrationProcessCompleted : RegistrationEvent()
        object ErrorSnackbarDismissed : RegistrationEvent()
    }
}
