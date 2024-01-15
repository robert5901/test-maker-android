package com.example.testmaker.network.repositories

import com.example.testmaker.models.admin.TeacherBody
import com.example.testmaker.models.users.Teacher
import com.example.testmaker.network.models.ApiResponse

interface AdminRepository {
    suspend fun delete(id: String): ApiResponse<Unit>

    suspend fun updateTeachers(): ApiResponse<List<Teacher>>

    suspend fun changeTeacher(id: String, teacherBody: TeacherBody): ApiResponse<Unit>

    suspend fun registerTeacher(teacherBody: TeacherBody): ApiResponse<Unit>
}