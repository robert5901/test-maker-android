package com.example.testmaker.core.errors

import kotlinx.coroutines.flow.SharedFlow

interface ErrorManager {
    val errors: SharedFlow<ErrorManagerError>
    fun showError(errorManagerError: ErrorManagerError)
}