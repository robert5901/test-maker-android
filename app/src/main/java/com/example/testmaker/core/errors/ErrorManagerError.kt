package com.example.testmaker.core.errors

import android.content.Context
import androidx.annotation.StringRes
import kotlin.coroutines.cancellation.CancellationException

sealed class ErrorManagerError {
    data class ThrowableError(val throwable: Throwable): ErrorManagerError()
    data class ResError(@StringRes val stringId: Int): ErrorManagerError()
    data class ResErrorWithParam(@StringRes val stringId: Int, val param: String): ErrorManagerError()
    sealed class ServerError(val responseCode: Int?): ErrorManagerError() {
        data class ServerStringError(val error: String, val responseCodeId: Int?): ServerError(responseCodeId)
        data class ServerResStringError(@StringRes val errorStringId: Int, val responseCodeId: Int?): ServerError(responseCodeId)
        data class ServerServiceError(val error: String?, val responseCodeId: Int?): ServerError(responseCodeId)
    }

    fun getLocalizedString(context: Context): String? {
        return when (this) {
            is ThrowableError -> {
                if (this.throwable is CancellationException) {
                    null
                } else {
                    throwable.localizedMessage
                }
            }

            is ResError -> {
                context.getString(stringId)
            }

            is ResErrorWithParam -> {
                context.getString(stringId, param)
            }

            is ServerError -> {
                when (this) {
                    is ServerError.ServerStringError -> {
                        error
                    }

                    is ServerError.ServerResStringError -> {
                        context.getString(errorStringId, responseCodeId.toString())
                    }

                    is ServerError.ServerServiceError -> null
                }
            }
        }
    }
}