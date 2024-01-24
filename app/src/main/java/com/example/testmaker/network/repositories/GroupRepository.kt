package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.Group
import com.example.testmaker.network.models.ApiResponse

interface GroupRepository {
    suspend fun getGroups(): ApiResponse<List<Group>>

    suspend fun deleteGroup(id: String): ApiResponse<List<Group>>

    suspend fun addGroup(name: String): ApiResponse<List<Group>>
}