package org.example.tourbookingkmp.di

import org.example.tourbookingkmp.data.repositories.TourDetailsRepositoryImpl
import org.example.tourbookingkmp.data.repositories.TourRepositoryImpl
import org.example.tourbookingkmp.domain.interfaces.repositories.TourDetailsRepository
import org.example.tourbookingkmp.domain.interfaces.repositories.TourRepository
import org.example.tourbookingkmp.domain.usecases.GetAllToursUseCase
import org.example.tourbookingkmp.domain.usecases.GetTourDetailsByIdUseCase
import org.example.tourbookingkmp.domain.usecases.SearchTourByCityUseCase
import org.example.tourbookingkmp.presentation.viewModels.GetAllToursViewModel
import org.example.tourbookingkmp.presentation.viewModels.GetTourDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Репозитории
    single<TourRepository> { TourRepositoryImpl() }
    single<TourDetailsRepository> { TourDetailsRepositoryImpl() }

    // UseCases
    factory { GetAllToursUseCase(get()) }
    factory { GetTourDetailsByIdUseCase(get()) }
    factory { SearchTourByCityUseCase() }

    // ViewModels
    viewModel { GetAllToursViewModel(get(), get()) }
    viewModel { (tourId: Comparable<*>) ->
        GetTourDetailsViewModel(tourId = tourId, useCase = get())
    }
}