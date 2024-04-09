package com.example.testmaker.network.services

import com.example.testmaker.models.student.Group
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface GroupService {
    @GET("/groups")
    suspend fun getGroups(): Response<List<Group>>

    @POST("/groups/create")
    suspend fun createGroup(name: String): Response<List<Group>>

    @DELETE("/groups/delete")
    suspend fun deleteGroup(id: String): Response<List<Group>>
}