package com.example.testmaker.di.modules

import com.example.testmaker.core.keyStore.KeyStore
import com.example.testmaker.core.keyStore.KeyStoreImpl
import com.example.testmaker.network.repositories.AuthRepository
import com.example.testmaker.network.repositories.AuthRepositoryImpl
import com.example.testmaker.network.services.AuthService
import com.example.testmaker.ui.auth.registration.viewModels.RegisterViewModel
import com.example.testmaker.ui.auth.viewModels.AuthViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single { provideRegisterRepository(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single { get<Cicerone<Router>>().router }
    single<KeyStore> { KeyStoreImpl() }

    viewModel { RegisterViewModel(get(), get(), get(), get()) }
    viewModel { AuthViewModel(get(), get(), get()) }
}

private fun provideRegisterRepository(retrofit: Retrofit): AuthService =
    retrofit.create(AuthService::class.java)