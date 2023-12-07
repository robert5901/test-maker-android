package com.example.testmaker.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import org.koin.java.KoinJavaComponent

class ErrorInterceptor: Interceptor {
    companion object {
        const val UNAUTHORISED_ERR_CODE = 401
        const val APP_UPDATE_ERR_CODE = 410
    }


//    private val appUpdateUseCase: AppUpdateUseCase by KoinJavaComponent.inject(AppUpdateUseCase::class.java)
//    private val authUseCase: AuthUseCase by KoinJavaComponent.inject(AuthUseCase::class.java)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code) {
            APP_UPDATE_ERR_CODE -> {
                // TODO
            }
            UNAUTHORISED_ERR_CODE -> {
//                if (response.request.url.encodedPath.en/dsWith(AuthService.refreshPath)) {
//                    authUseCase.completeLogout()
//                }
            }
        }

        return response
    }
}