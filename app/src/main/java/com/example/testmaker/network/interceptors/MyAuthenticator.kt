package com.example.testmaker.network.interceptors

import com.example.testmaker.core.keyStore.KeyStore
import com.example.testmaker.models.auth.AuthInfo
import com.example.testmaker.models.auth.UserRole
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.SuperRepository
import com.example.testmaker.network.services.RefreshBody
import com.example.testmaker.network.services.RefreshService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MyAuthenticator : Authenticator, SuperRepository(), KoinComponent {

    private val refreshService by inject<RefreshService>()
    private val keyStore by inject<KeyStore>()

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentAuthInfo = keyStore.getAuthInfo() ?: return null
        val result = runBlocking {
            safeApiRequest {
                request(refreshService.refresh(RefreshBody(currentAuthInfo.refreshCode)))
            }
        }
        if (result is ApiResponse.Success) {
            val resultData = result.data
            keyStore.setAuthInfo(
                AuthInfo(
                    resultData.accessCode,
                    resultData.refreshCode,
                    UserRole.fromString(resultData.userRole)
                )
            )
            return response.request.newBuilder().header("Authorization", "Bearer ${result.data.accessCode}").build()
        }
        return null
    }

}