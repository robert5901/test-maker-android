package com.example.testmaker.di

import android.app.Application
import com.example.testmaker.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun Application.initKoin() {
    startKoin {
        androidContext(this@initKoin)
        modules(modules)
    }
}

private val modules = listOf(
    networkModule,
    navigationModule,
    coreModule,
    adminModule,
    authModule,
    teacherModule
)