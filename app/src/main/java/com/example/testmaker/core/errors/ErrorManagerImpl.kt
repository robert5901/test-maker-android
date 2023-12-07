package com.example.testmaker.core.errors

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class ErrorManagerImpl : ErrorManager {
    override val errors = MutableSharedFlow<ErrorManagerError>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND
    )

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun showError(errorManagerError: ErrorManagerError) {
        scope.launch {
            errors.emit(errorManagerError)
        }
    }
}