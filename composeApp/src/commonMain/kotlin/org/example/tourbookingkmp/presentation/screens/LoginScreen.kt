package org.example.tourbookingkmp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.tourbookingkmp.navigation.old.Routes
import org.example.tourbookingkmp.presentation.viewModels.LoginUserByEmailViewModel.LoginScreenEvent
import org.example.tourbookingkmp.presentation.viewModels.LoginUserByEmailViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginUserByEmailViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { eventMessage ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = eventMessage,
                    withDismissAction = true
                )
            }
        }
    }

    LaunchedEffect(uiState.loginProcessCompleted, uiState.loggedInUser, uiState.error) {
        if (uiState.loginProcessCompleted) {
            if (uiState.loggedInUser != null) {
                navController.navigate(Routes.TOURS_LIST) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
                viewModel.onEvent(LoginScreenEvent.ResetLoginProcess)
            } else if (uiState.error != null) {
                viewModel.onEvent(LoginScreenEvent.ResetLoginProcess)
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Вход",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEvent(LoginScreenEvent.EmailChanged(it)) },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.error != null && uiState.email.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onEvent(LoginScreenEvent.PasswordChanged(it)) },
                label = { Text("Пароль") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = uiState.error != null && uiState.password.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            uiState.error?.let { errorText ->
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
                onClick = { viewModel.onEvent(LoginScreenEvent.LoginClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Войти")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ClickableText(
                    text = AnnotatedString("Забыли пароль?"),
                    onClick = {
                        // TODO: Доделать логику и навигацию восстановления пароля
                    },
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline
                    )
                )

                ClickableText(
                    text = AnnotatedString("Регистрация"),
                    onClick = {
                        navController.navigate(Routes.REGISTER) {
                             popUpTo(Routes.LOGIN) { inclusive = true }
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
}

