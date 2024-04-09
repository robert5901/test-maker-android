package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.Group
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.services.GroupService

class GroupRepositoryImpl(private val apiService: GroupService): SuperRepository(), GroupRepository {
    override suspend fun getGroups(): ApiResponse<List<Group>> = safeApiRequest {
        request(apiService.getGroups())
    }

    override suspend fun deleteGroup(id: String) = safeApiRequest {
        request(apiService.deleteGroup(id))
    }

    override suspend fun addGroup(name: String)= safeApiRequest {
        request(apiService.createGroup(name))
    }
}