package org.example.tourbookingkmp.navigation.nav3

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.example.tourbookingkmp.presentation.screens.TourDetailsScreen
import org.example.tourbookingkmp.presentation.screens.ToursListScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.TourList::class, Route.TourList.serializer())
                    subclass(Route.TourDetails::class, Route.TourDetails.serializer())
                }
            }
        },
        Route.TourList
    )
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                is Route.TourList -> {
                    NavEntry(key) {
                        ToursListScreen(
                            onTourDetailsClick = {
                                backStack.add(Route.TourDetails(it))
                            }
                        )
                    }
                }

                is Route.TourDetails -> {
                    NavEntry(key) {
                        TourDetailsScreen(key.id)
                    }
                }

                else -> error("Unknown NavKey: $key")
            }
        }
    )
}