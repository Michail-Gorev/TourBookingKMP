package org.example.tourbookingkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*

import cafe.adriel.voyager.navigator.Navigator

import org.example.tourbookingkmp.screens.ToursListScreen

import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(
            screen = ToursListScreen()
        )
    }
}