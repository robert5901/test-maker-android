package com.example.testmaker.di.modules

import com.example.testmaker.ui.teacher.testList.viewModels.TeacherTestListViewModel
import com.example.testmaker.ui.teacher.testQuestionList.viewModels.TeacherTestQuestionListViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val teacherModule = module {
    single { get<Cicerone<Router>>().router }

    viewModel { TeacherTestQuestionListViewModel() }
    viewModel { TeacherTestListViewModel() }
}