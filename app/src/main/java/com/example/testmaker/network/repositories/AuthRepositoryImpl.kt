package com.example.testmaker.network.repositories

import com.example.testmaker.models.auth.AuthLogin
import com.example.testmaker.models.users.Student
import com.example.testmaker.network.services.AuthService

class AuthRepositoryImpl(private val apiService: AuthService): SuperRepository(), AuthRepository {
    override suspend fun createStudent(student: Student) = safeApiRequest {
        request(apiService.createStudent(student))
    }

    override suspend fun login(login: AuthLogin) = safeApiRequest {
        request(apiService.login(login))
    }
}