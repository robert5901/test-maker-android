package com.example.testmaker.core.keyStore

import com.example.testmaker.models.auth.AuthInfo
import com.example.testmaker.models.auth.UserRole
import com.orhanobut.hawk.Hawk

class KeyStoreImpl: KeyStore {
    companion object {
        private const val accessTokenKey = "accessToken"
        private const val refreshTokenKey = "accessToken"
        private const val userRoleKey = "accessToken"
    }

    private var accessToken: String?
        get() = Hawk.get(accessTokenKey, null)
        set(value) {
            Hawk.put(accessTokenKey, value)
        }

    private var refreshToken: String?
        get() = Hawk.get(refreshTokenKey, null)
        set(value) {
            Hawk.put(refreshTokenKey, value)
        }

    private var userRole: UserRole?
        get() = Hawk.get(userRoleKey, null)
        set(value) {
            Hawk.put(userRoleKey, value)
        }

    override fun setAuthInfo(authInfo: AuthInfo) {
        accessToken = authInfo.accessCode
        refreshToken = authInfo.refreshCode
        userRole = authInfo.userRole
    }

    override fun getAuthInfo(): AuthInfo? {
        val accessToken = this.accessToken ?: return null
        val refreshToken = this.refreshToken ?: return null
        val userRole = this.userRole ?: return null

        return AuthInfo(accessToken, refreshToken, userRole)
    }
}