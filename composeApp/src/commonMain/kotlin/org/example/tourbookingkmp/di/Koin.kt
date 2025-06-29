package org.example.tourbookingkmp.di

import org.example.tourbookingkmp.repositories.TourDetailsRepository
import org.example.tourbookingkmp.repositories.TourRepository
import org.example.tourbookingkmp.usecases.GetAllToursUseCase
import org.example.tourbookingkmp.usecases.GetTourDetailsByIdUseCase
import org.example.tourbookingkmp.viewModels.GetAllToursViewModel
import org.example.tourbookingkmp.viewModels.GetTourDetailsViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    // Репозитории
    single { TourRepository() }
    single { TourDetailsRepository() }

    // UseCases
    factory { GetAllToursUseCase(get()) }
    factory { GetTourDetailsByIdUseCase(get()) }

    // ViewModels
    factory { GetAllToursViewModel(get()) }
    factory { (tourId: Comparable<*>) ->
        GetTourDetailsViewModel(tourId = tourId, useCase = get())
    }
}

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}