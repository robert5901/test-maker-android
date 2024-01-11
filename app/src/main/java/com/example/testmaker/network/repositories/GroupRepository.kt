package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.Group
import com.example.testmaker.network.models.ApiResponse

interface GroupRepository {
    suspend fun getGroups(): ApiResponse<List<Group>>
}