package com.example.testmaker.di.modules

import com.example.testmaker.network.interceptors.MyAuthenticator
import com.example.testmaker.network.models.ApiServer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val defaultApiUrl = ApiServer.base_url

val networkModule = module {
    single { provideMoshi() }
    single { provideRetrofitBuilder(get()) }
    single { provideRetrofit(get(), get()) }
    single { provideOkHttp() }
}

private fun provideMoshi(): Moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
    .authenticator(MyAuthenticator())
    .build()

private fun provideRetrofit(
    builder: Retrofit.Builder,
    okHttpClient: OkHttpClient,
): Retrofit = builder.client(okHttpClient).build()

private fun provideRetrofitBuilder(
    moshi: Moshi,
): Retrofit.Builder =
    Retrofit.Builder()
        .baseUrl(defaultApiUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))