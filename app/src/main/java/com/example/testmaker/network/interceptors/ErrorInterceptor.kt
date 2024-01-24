package com.example.testmaker.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import org.koin.java.KoinJavaComponent

class ErrorInterceptor: Interceptor {
    companion object {
        const val UNAUTHORISED_ERR_CODE = 401
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code) {
            UNAUTHORISED_ERR_CODE -> {
//                if (response.request.url.encodedPath.en/dsWith(AuthService.refreshPath)) {
//                    authUseCase.completeLogout()
//                }
            }
        }

        return response
    }
}