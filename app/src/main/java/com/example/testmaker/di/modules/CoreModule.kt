package com.example.testmaker.di.modules

import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.core.errors.ErrorManagerImpl
import com.example.testmaker.ui.main.viewModels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {
    single<ErrorManager> { ErrorManagerImpl() }

    viewModel { MainViewModel(get()) }
}