package com.example.testmaker.network.repositories

import com.example.testmaker.models.auth.AuthInfo
import com.example.testmaker.models.auth.UserRole
import com.example.testmaker.models.student.Group
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.services.AuthService

class AuthRepositoryImpl(private val apiService: AuthService): SuperRepository(), AuthRepository {
    override suspend fun getGroups(): ApiResponse<List<Group>> {
        return ApiResponse.Success(
            listOf(
                Group("1", "4480"),
                Group("2", "4481"),
                Group("3", "4482"),
                Group("4", "4483"),
                Group("5", "4484"),
                Group("6", "4480"),
                Group("7", "4481")
            )
        )
//        request(apiService.getGroups())
    }

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