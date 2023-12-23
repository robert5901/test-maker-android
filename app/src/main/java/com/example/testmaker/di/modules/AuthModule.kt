package com.example.testmaker.di.modules

import com.example.testmaker.network.repositories.RegisterRepository
import com.example.testmaker.network.repositories.RegisterRepositoryImpl
import com.example.testmaker.network.services.RegisterService
import com.example.testmaker.ui.auth.registration.viewModels.RegisterViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single { provideRegisterRepository(get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(get()) }
    single { get<Cicerone<Router>>().router }

    viewModel { RegisterViewModel(get(), get(), get()) }
}

private fun provideRegisterRepository(retrofit: Retrofit): RegisterService =
    retrofit.create(RegisterService::class.java)