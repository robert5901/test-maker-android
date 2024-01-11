package com.example.testmaker.network.repositories

import com.example.testmaker.models.auth.AuthInfo
import com.example.testmaker.models.auth.UserRole
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.services.AuthService

class AuthRepositoryImpl(private val apiService: AuthService): SuperRepository(), AuthRepository {
    override suspend fun createStudent(
        groupId: String,
        login: String,
        name: String,
        password: String
    ): ApiResponse<Unit> {
    return ApiResponse.Success(Unit)
//        request(apiService.createStudent(groupId, login, name, password))
    }

    override suspend fun login(login: String, password: String): ApiResponse<AuthInfo> {
    return ApiResponse.Success(AuthInfo("", "", UserRole.TEACHER))
//        request(apiService.login(login, password))
    }
}