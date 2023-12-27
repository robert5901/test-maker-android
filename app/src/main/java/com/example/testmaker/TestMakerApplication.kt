package com.example.testmaker

import android.app.Application
import com.example.testmaker.di.initKoin
import com.orhanobut.hawk.Hawk

class TestMakerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
        Hawk.init(this).build()
    }
}