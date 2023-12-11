package com.example.testmaker.di.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

val navigationModule = module {
    single { Cicerone.create() }
    single { createRouter(get()) }
    single { createNavigatorHolder(get()) }
}

fun createRouter(cicerone: Cicerone<Router>) = cicerone.router
fun createNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()