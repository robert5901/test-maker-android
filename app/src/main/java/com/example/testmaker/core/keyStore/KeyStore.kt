package com.example.testmaker.core.keyStore

import com.example.testmaker.models.auth.AuthInfo

interface KeyStore {
    fun setAuthInfo(authInfo: AuthInfo)
    fun getAuthInfo(): AuthInfo?
}