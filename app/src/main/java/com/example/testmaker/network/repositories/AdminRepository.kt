package com.example.testmaker.network.repositories

import com.example.testmaker.models.users.Teacher
import com.example.testmaker.network.models.ApiResponse

interface AdminRepository {
    suspend fun delete(id: String): ApiResponse<Unit>

    suspend fun updateTeachers(): ApiResponse<List<Teacher>>

    suspend fun changeTeacher(id: String, login: String, name: String, password: String): ApiResponse<Unit>

    suspend fun registerTeacher(login: String, name: String, password: String): ApiResponse<Unit>
}