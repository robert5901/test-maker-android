package com.example.testmaker.network.repositories

import com.example.testmaker.R
import com.example.testmaker.network.models.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import com.example.testmaker.core.errors.ErrorManagerError
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

open class SuperRepository {
    suspend inline fun <reified T> safeApiRequest(crossinline block: suspend SafeApiRequestScope.() -> ApiResponse<T>): ApiResponse<T> =
        withContext(Dispatchers.IO) {
            try {
                block(SafeApiRequestScope)
            } catch (e: Exception) {
                when (e) {
                    is SocketTimeoutException -> {
                        ApiResponse.Error(ErrorManagerError.ResError(R.string.error_server_timeout))
                    }
                    is SSLHandshakeException, is UnknownHostException, is ConnectException -> {
                        ApiResponse.Error(ErrorManagerError.ResError(R.string.error_unknown_exception))
                    }
                    else -> {
                        ApiResponse.Error(ErrorManagerError.ThrowableError(e))
                    }
                }
            }
        }

    object SafeApiRequestScope {
        fun requestEmpty(response: Response<Unit>): ApiResponse<Unit> = ApiResponse.createEmpty(response)
        inline fun <reified T> request(response: Response<T>): ApiResponse<T> = ApiResponse.create(response)
    }
}
