package com.example.testmaker.network.services

import com.example.testmaker.models.admin.Teacher
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AdminService {
    @DELETE("admin/teacher/{id}")
    suspend fun deleteTeacher(@Path(value = "id", encoded = true) id: String): Response<Unit>

    @GET("admin/teachers")
    suspend fun updateTeachers(): Response<List<Teacher>>

    @PUT("admin/teacher/{id}")
    suspend fun changeTeacher(@Path(value = "id", encoded = true) id: String,
                              @Query(value = "login", encoded = true) login: String,
                              @Query(value = "name", encoded = true) name: String,
                              @Query(value = "password", encoded = true) password: String): Response<Unit>

    @POST("admin/teacher/register")
    suspend fun registerTeacher(@Query(value = "login", encoded = true) login: String,
                                @Query(value = "name", encoded = true) name: String,
                                @Query(value = "password", encoded = true) password: String): Response<Unit>
}