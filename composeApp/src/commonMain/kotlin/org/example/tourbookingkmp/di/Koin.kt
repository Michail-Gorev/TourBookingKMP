package org.example.tourbookingkmp.di

import org.example.tourbookingkmp.data.networkhelpers.AuthorizationHelperImpl
import org.example.tourbookingkmp.data.repositories.TourDetailsRepositoryImpl
import org.example.tourbookingkmp.data.repositories.TourRepositoryImpl
import org.example.tourbookingkmp.data.repositories.UserRepositoryImpl
import org.example.tourbookingkmp.data.repositories.UserTokenRepositoryImpl
import org.example.tourbookingkmp.domain.interfaces.networkhelpers.AuthorizationHelper
import org.example.tourbookingkmp.domain.interfaces.repositories.TourDetailsRepository
import org.example.tourbookingkmp.domain.interfaces.repositories.TourRepository
import org.example.tourbookingkmp.domain.interfaces.repositories.UserRepository
import org.example.tourbookingkmp.domain.interfaces.repositories.UserTokenRepository
import org.example.tourbookingkmp.domain.models.User
import org.example.tourbookingkmp.domain.usecases.GetAllToursUseCase
import org.example.tourbookingkmp.domain.usecases.GetTourDetailsByIdUseCase
import org.example.tourbookingkmp.domain.usecases.LoginUserByEmailUseCase
import org.example.tourbookingkmp.domain.usecases.RegisterUserByEmailUseCase
import org.example.tourbookingkmp.domain.usecases.SearchTourByCityUseCase
import org.example.tourbookingkmp.presentation.viewModels.GetAllToursViewModel
import org.example.tourbookingkmp.presentation.viewModels.GetTourDetailsViewModel
import org.example.tourbookingkmp.presentation.viewModels.LoginUserByEmailViewModel
import org.example.tourbookingkmp.presentation.viewModels.RegisterUserByEmailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<AuthorizationHelper> { AuthorizationHelperImpl() }

    // Репозитории
    single<TourRepository> { TourRepositoryImpl() }
    single<TourDetailsRepository> { TourDetailsRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserTokenRepository> { UserTokenRepositoryImpl() }

    // UseCases
    factory { GetAllToursUseCase(get()) }
    factory { GetTourDetailsByIdUseCase(get()) }
    factory { SearchTourByCityUseCase() }
    factory { RegisterUserByEmailUseCase(get()) }
    factory { LoginUserByEmailUseCase(get(), get()) }

    // ViewModels
    viewModel { GetAllToursViewModel(get(), get()) }
    viewModel { (tourId: Comparable<*>) ->
        GetTourDetailsViewModel(tourId = tourId, useCase = get())
    }
    viewModel {
        LoginUserByEmailViewModel(get())
    }
    viewModel {
        RegisterUserByEmailViewModel(
            get()
        )
    }
}