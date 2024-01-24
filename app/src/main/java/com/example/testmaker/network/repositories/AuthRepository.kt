package com.example.testmaker.network.repositories

import com.example.testmaker.models.auth.AuthInfo
import com.example.testmaker.models.auth.AuthLogin
import com.example.testmaker.models.users.Student
import com.example.testmaker.network.models.ApiResponse

interface AuthRepository {
    suspend fun createStudent(student: Student): ApiResponse<Unit>

    suspend fun login(login: AuthLogin): ApiResponse<AuthInfo>
}