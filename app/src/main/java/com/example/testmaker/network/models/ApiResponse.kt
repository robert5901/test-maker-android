package com.example.testmaker.network.models

import com.example.testmaker.R
import com.example.testmaker.network.interceptors.ErrorInterceptor
import com.squareup.moshi.Moshi
import okhttp3.Request
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent
import retrofit2.Response
import com.example.testmaker.core.errors.ErrorManagerError

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val errorManagerError: ErrorManagerError) : ApiResponse<Nothing>()

    companion object {
        inline fun <reified T> create(response: Response<T>): ApiResponse<T> {
            return if(response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Success(body)
                } else {
                    Error(ErrorManagerError.ServerError.ServerStringError("empty body!", response.code()))
                }
            } else {
                processErrorResponse(response.raw().request, response.errorBody(), response.code())
            }
        }

        fun createEmpty(response: Response<Unit>): ApiResponse<Unit> {
            return if(response.isSuccessful) {
                Success(Unit)
            } else {
                processErrorResponse(response.raw().request, response.errorBody(), response.code())
            }
        }

        inline fun <reified T> processErrorResponse(request: Request, responseBody: ResponseBody?, responseCode: Int): ApiResponse<T> {
            val errorBodyStr = responseBody?.string() ?: ""
            val moshi = KoinJavaComponent.get<Moshi>(Moshi::class.java)
            val adapter = moshi.adapter(ServerErrorResponse::class.java)

            var errorMessage: String? = null
            try {
                val serverErrorResponse = adapter.fromJson(errorBodyStr)
                errorMessage = serverErrorResponse?.message!!
            } catch (_: Exception) { } // TODO

            return when {
                isServiceError(request, responseCode) -> {
                    Error(ErrorManagerError.ServerError.ServerServiceError(errorMessage, responseCode))
                }
                errorMessage != null -> {
                    Error(ErrorManagerError.ServerError.ServerStringError(errorMessage, responseCode))
                }
                else -> {
                    Error(ErrorManagerError.ServerError.ServerResStringError(R.string.error_server_with_code, responseCode))
                }
            }
        }

        fun isServiceError(request: Request, responseCode: Int): Boolean =
            when (responseCode) {
                ErrorInterceptor.UNAUTHORISED_ERR_CODE -> {
                    // TODO
                    false
                }
                ErrorInterceptor.APP_UPDATE_ERR_CODE -> {
                    true
                }
                else -> {
                    false
                }
            }
    }
}