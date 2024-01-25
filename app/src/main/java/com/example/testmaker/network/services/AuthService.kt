package com.example.testmaker.network.services

import com.example.testmaker.models.auth.AuthInfo
import com.example.testmaker.models.auth.AuthLogin
import com.example.testmaker.models.auth.RefreshBody
import com.example.testmaker.models.users.Student
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/register/student")
    suspend fun createStudent(@Body student: Student): Response<Unit>

    @POST("/auth/login")
    suspend fun login(@Body login: AuthLogin): Response<AuthInfo>
}

interface RefreshService {
    @POST("/auth/refresh")
    suspend fun refresh(@Body body: RefreshBody): Response<AuthInfo>
}