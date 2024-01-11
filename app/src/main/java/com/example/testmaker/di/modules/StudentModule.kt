package com.example.testmaker.di.modules

import com.example.testmaker.network.repositories.StudentRepository
import com.example.testmaker.network.repositories.StudentRepositoryImpl
import com.example.testmaker.network.services.StudentService
import com.example.testmaker.ui.student.results.viewModels.StudentResultsViewModel
import com.example.testmaker.ui.student.testInfo.viewModels.StudentTestInfoViewModel
import com.example.testmaker.ui.student.testList.viewModels.StudentTestListViewModel
import com.example.testmaker.ui.student.testQuestion.viewModels.StudentTestQuestionViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val studentModule = module {
    single { provideStudentRepository(get()) }
    single<StudentRepository> { StudentRepositoryImpl(get()) }
    single { get<Cicerone<Router>>().router }

    viewModel { StudentTestListViewModel(get(), get()) }
    viewModel { StudentTestInfoViewModel(get(), get(), get()) }
    viewModel { StudentResultsViewModel(get(), get()) }
    viewModel { StudentTestQuestionViewModel(get(), get(), get()) }
}

private fun provideStudentRepository(retrofit: Retrofit): StudentService =
    retrofit.create(StudentService::class.java)