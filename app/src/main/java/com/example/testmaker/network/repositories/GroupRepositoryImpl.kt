package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.Group
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.services.GroupService

class GroupRepositoryImpl(private val apiService: GroupService): SuperRepository(), GroupRepository {
    override suspend fun getGroups(): ApiResponse<List<Group>> = safeApiRequest {
        request(apiService.getGroups())
    }

    override suspend fun deleteGroup(id: String): ApiResponse<List<Group>> {
        // test data
        return ApiResponse.Success(listOf(
            Group("efe430b7-82ac-46e1-b019-99e2a6e3843f", "4480"),
            Group("bcc68686-418e-456a-85ba-0c8df4c10089", "4481"),
            Group("2b612de8-0975-4ee0-ae45-3c3b87fdc95c", "4482"),
            Group("df701025-e0e4-45a2-983d-9773ccadb3c4", "4483"),
            Group("6107e828-e24c-4404-8352-d9810167451a", "4484"),
        ))
    }

    override suspend fun addGroup(name: String): ApiResponse<List<Group>> {
        // test data
        return ApiResponse.Success(listOf(
            Group("efe430b7-82ac-46e1-b019-99e2a6e3843f", "4480"),
            Group("bcc68686-418e-456a-85ba-0c8df4c10089", "4481"),
            Group("2b612de8-0975-4ee0-ae45-3c3b87fdc95c", "4482"),
            Group("df701025-e0e4-45a2-983d-9773ccadb3c4", "4483"),
            Group("6107e828-e24c-4404-8352-d9810167451a", "4484"),
            Group("6107e828-e24c-4404-8352-d9810167451d", "4485"),
        ))
    }
}