package com.example.testmaker.network.services

import com.example.testmaker.models.student.Group
import retrofit2.Response
import retrofit2.http.GET

interface GroupService {
    @GET("groups")
    suspend fun getGroups(): Response<List<Group>>
}