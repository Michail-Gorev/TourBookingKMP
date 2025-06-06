package org.example.tourbookingkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.tourbookingkmp.screens.ToursListScreen
import org.example.tourbookingkmp.viewModels.GetAllToursViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        ToursListScreen(GetAllToursViewModel())
    }
}
