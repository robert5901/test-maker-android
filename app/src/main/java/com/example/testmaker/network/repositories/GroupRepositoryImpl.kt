package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.Group
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.services.GroupService

class GroupRepositoryImpl(private val apiService: GroupService): SuperRepository(), GroupRepository {
    override suspend fun getGroups(): ApiResponse<List<Group>> {
        return ApiResponse.Success(
            listOf(
                Group("1", "4480"),
                Group("2", "4481"),
                Group("3", "4482"),
                Group("4", "4483"),
                Group("5", "4484"),
                Group("6", "4480"),
                Group("7", "4481")
            )
        )
//        request(apiService.getGroups())
    }
}