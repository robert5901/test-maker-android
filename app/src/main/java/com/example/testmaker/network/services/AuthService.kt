package com.example.testmaker.network.services

import com.example.testmaker.models.student.Groups
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @GET("groups")
    suspend fun getGroups(): Response<List<Groups>>

    @POST("/auth/register/student")
    suspend fun createStudent(@Query(value = "groupId", encoded = true) groupId: String,
                              @Query(value = "login", encoded = true) login: String,
                              @Query(value = "name", encoded = true) name: String,
                              @Query(value = "password", encoded = true) password: String): Response<Unit>

    @POST("/auth/login")
    suspend fun login(@Query(value = "login", encoded = true) login: String,
                      @Query(value = "password", encoded = true) password: String): Response<Unit>
}