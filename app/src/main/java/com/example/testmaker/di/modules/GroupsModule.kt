package com.example.testmaker.di.modules

import com.example.testmaker.network.repositories.GroupRepository
import com.example.testmaker.network.repositories.GroupRepositoryImpl
import com.example.testmaker.network.services.GroupService
import org.koin.dsl.module
import retrofit2.Retrofit

val groupsModule = module {
    single { provideGroupRepository(get()) }
    single<GroupRepository> { GroupRepositoryImpl(get()) }
}

private fun provideGroupRepository(retrofit: Retrofit): GroupService =
    retrofit.create(GroupService::class.java)