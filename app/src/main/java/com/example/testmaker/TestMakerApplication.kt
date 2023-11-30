package com.example.testmaker

import android.app.Application
import com.example.testmaker.di.initKoin

class TestMakerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}