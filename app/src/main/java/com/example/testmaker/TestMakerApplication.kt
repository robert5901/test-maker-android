package com.example.testmaker

import android.app.Application
import android.content.Context
import com.example.testmaker.di.initKoin
import com.orhanobut.hawk.Hawk

class TestMakerApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        initKoin()
        Hawk.init(this).build()
    }

    companion object {
        private lateinit var instance: TestMakerApplication

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}