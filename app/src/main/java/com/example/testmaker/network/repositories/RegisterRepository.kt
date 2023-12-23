package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.Groups
import com.example.testmaker.network.models.ApiResponse

interface RegisterRepository {
    suspend fun getGroups(): ApiResponse<List<Groups>>

    suspend fun createStudent(groupId: String, login: String, name: String, password: String): ApiResponse<Unit>
}