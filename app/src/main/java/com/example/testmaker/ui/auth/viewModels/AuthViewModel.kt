package com.example.testmaker.ui.auth.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.core.keyStore.KeyStore
import com.example.testmaker.models.auth.AuthInfo
import com.example.testmaker.models.auth.AuthLogin
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val errorManager: ErrorManager,
    private val keyStore: KeyStore
): ViewModel() {
    private val _authInfo = MutableStateFlow<AuthInfo?>(null)
    val authInfo = _authInfo.asStateFlow()

    val loading = MutableStateFlow(false)

    fun login(login: AuthLogin) {
        viewModelScope.launch {
            loading.emit(true)

            val response = authRepository.login(login)
            loading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _authInfo.emit(response.data)
                    keyStore.setAuthInfo(response.data)
                }
                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }
}