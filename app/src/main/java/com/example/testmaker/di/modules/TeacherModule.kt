package com.example.testmaker.di.modules

import com.example.testmaker.network.repositories.TeacherRepository
import com.example.testmaker.network.repositories.TeacherRepositoryImpl
import com.example.testmaker.network.services.TeacherService
import com.example.testmaker.ui.teacher.configureTest.viewModels.TeacherTestConfigureViewModel
import com.example.testmaker.ui.teacher.results.testList.viewModels.TeacherResultsTestListViewModel
import com.example.testmaker.ui.teacher.results.testResults.viewModels.TeacherTestResultsViewModel
import com.example.testmaker.ui.teacher.testList.viewModels.TeacherTestListViewModel
import com.example.testmaker.ui.teacher.testQuestion.viewModels.TeacherTestQuestionViewModel
import com.example.testmaker.ui.teacher.testQuestionList.viewModels.TeacherTestQuestionListViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val teacherModule = module {
    single { provideTeacherRepository(get()) }
    single<TeacherRepository> { TeacherRepositoryImpl(get()) }
    single { get<Cicerone<Router>>().router }

    viewModel { TeacherTestQuestionListViewModel(get(), get()) }
    viewModel { TeacherTestListViewModel(get(), get(), get()) }
    viewModel { TeacherTestConfigureViewModel(get(), get(), get(), get()) }
    viewModel { TeacherResultsTestListViewModel(get(), get()) }
    viewModel { TeacherTestQuestionViewModel(get(), get(), get()) }
    viewModel { TeacherTestResultsViewModel(get(), get()) }
}

private fun provideTeacherRepository(retrofit: Retrofit): TeacherService =
    retrofit.create(TeacherService::class.java)