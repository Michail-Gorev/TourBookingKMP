package org.example.tourbookingkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.tourbookingkmp.di.appModule
import org.example.tourbookingkmp.navigation.AppNavigation

import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
        },
        content = { MaterialTheme {  AppNavigation() } }
    )
}