package com.example.testmaker.di.modules

import com.example.testmaker.network.repositories.AdminRepository
import com.example.testmaker.network.repositories.AdminRepositoryImpl
import com.example.testmaker.network.services.AdminService
import com.example.testmaker.ui.admin.addTeacher.viewModels.AddTeacherViewModel
import com.example.testmaker.ui.admin.groups.adapters.AdminGroupsViewModel
import com.example.testmaker.ui.admin.teacherList.viewModels.AdminTeacherListViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val adminModule = module {
    single { provideAdminRepository(get()) }
    single<AdminRepository> { AdminRepositoryImpl(get()) }
    single { get<Cicerone<Router>>().router }

    viewModel { AdminTeacherListViewModel(get(), get()) }
    viewModel { AddTeacherViewModel(get(), get(), get()) }
    viewModel { AdminGroupsViewModel(get(), get()) }
}

private fun provideAdminRepository(retrofit: Retrofit): AdminService =
    retrofit.create(AdminService::class.java)