package org.example.tourbookingkmp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.example.tourbookingkmp.navigation.old.Routes
import androidx.compose.foundation.text.KeyboardOptions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.presentation.viewModels.RegisterUserByEmailViewModel.RegistrationEvent
import org.example.tourbookingkmp.presentation.viewModels.RegisterUserByEmailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: RegisterUserByEmailViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.navigateToLogin) {
        if (uiState.navigateToLogin) {
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.REGISTER) { inclusive = true }
            }
            viewModel.onEvent(RegistrationEvent.RegistrationProcessCompleted)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message, withDismissAction = true)
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = uiState.firstName,
                onValueChange = { viewModel.onEvent(RegistrationEvent.FirstNameChanged(it)) },
                label = { Text("Имя") },
                singleLine = true,
                isError = uiState.registrationError?.contains("Имя", ignoreCase = true) == true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = uiState.lastName,
                onValueChange = { viewModel.onEvent(RegistrationEvent.LastNameChanged(it)) },
                label = { Text("Фамилия") },
                singleLine = true,
                isError = uiState.registrationError?.contains("Фамилия", ignoreCase = true) == true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEvent(RegistrationEvent.EmailChanged(it)) },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.registrationError?.contains("Email", ignoreCase = true) == true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onEvent(RegistrationEvent.PasswordChanged(it)) },
                label = { Text("Пароль") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.registrationError?.contains(
                    "Пароль",
                    ignoreCase = true
                ) == true ||
                        uiState.registrationError?.contains(
                            "Пароли не совпадают",
                            ignoreCase = true
                        ) == true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = { viewModel.onEvent(RegistrationEvent.ConfirmPasswordChanged(it)) },
                label = { Text("Подтвердите пароль") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.registrationError?.contains(
                    "Пароли не совпадают",
                    ignoreCase = true
                ) == true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            uiState.registrationError?.let { errorText ->
                Text(
                    text = errorText,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = { viewModel.onEvent(RegistrationEvent.RegisterClicked) },
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Зарегистрироваться")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ClickableText(
                text = AnnotatedString("Уже есть аккаунт? Войти"),
                onClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}
