package com.example.testmaker.di.modules

import com.example.testmaker.ui.teacher.createTest.viewModels.TeacherCreateTestViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val teacherModule = module {
    single { get<Cicerone<Router>>().router }

    viewModel { TeacherCreateTestViewModel() }
}