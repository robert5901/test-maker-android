package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.Groups
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.models.ApiServer
import com.example.testmaker.network.repositories.SuperRepository.SafeApiRequestScope.request
import com.example.testmaker.network.services.RegisterService

class RegisterRepositoryImpl(private val apiService: RegisterService): SuperRepository(), RegisterRepository {
    override suspend fun getGroups(): ApiResponse<List<Groups>> {
        return ApiResponse.Success(
            listOf(
                Groups("1", "4480"),
                Groups("2", "4481"),
                Groups("3", "4482"),
                Groups("4", "4483"),
                Groups("5", "4484"),
                Groups("6", "4480"),
                Groups("7", "4481")
            )
        )
//        request(apiService.getGroups())
    }

    override suspend fun createStudent(
        groupId: String,
        login: String,
        name: String,
        password: String
    ): ApiResponse<Unit> {
    return ApiResponse.Success(Unit)
//        request(apiService.createStudent(groupId, login, name, password))
    }
}