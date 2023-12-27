package com.example.testmaker.di.modules

import com.example.testmaker.network.models.ApiServer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val defaultApiUrl = ApiServer.base_url

val networkModule = module {
    single { provideMoshi() }
    single { provideRetrofit(get(), defaultApiUrl) }
}

private fun provideMoshi(): Moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private fun provideRetrofit(moshi: Moshi, defaultApiUrl: String): Retrofit =
    Retrofit.Builder()
        // TODO добавить okHttp interceptors
//        .client(client)  okHttp with interceptors надо???
        .baseUrl(defaultApiUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()