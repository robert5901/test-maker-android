package com.example.testmaker.ui.main.viewModels

import androidx.lifecycle.ViewModel
import com.example.testmaker.core.errors.ErrorManager

class MainViewModel(
    errorManager: ErrorManager
): ViewModel() {
    val errors = errorManager.errors
}