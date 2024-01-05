package com.example.testmaker.di.modules

import com.example.testmaker.ui.student.testList.viewModels.StudentTestListViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val studentModule = module {
    single { get<Cicerone<Router>>().router }

    viewModel { StudentTestListViewModel() }
}