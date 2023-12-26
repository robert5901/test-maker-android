package com.example.testmaker.network.repositories

import com.example.testmaker.models.auth.AuthInfo
import com.example.testmaker.models.student.Group
import com.example.testmaker.network.models.ApiResponse

interface AuthRepository {
    suspend fun getGroups(): ApiResponse<List<Group>>

    suspend fun createStudent(groupId: String, login: String, name: String, password: String): ApiResponse<Unit>

    suspend fun login(login: String, password: String): ApiResponse<AuthInfo>
}