package com.example.testmaker.network.repositories

import com.example.testmaker.models.admin.TeacherBody
import com.example.testmaker.network.services.AdminService

class AdminRepositoryImpl(private val apiService: AdminService): SuperRepository(), AdminRepository {
    override suspend fun delete(id: String) = safeApiRequest {
        requestEmpty(apiService.deleteTeacher(id))
    }

    override suspend fun updateTeachers() = safeApiRequest {
        request(apiService.updateTeachers())
    }

    override suspend fun changeTeacher(id: String, teacherBody: TeacherBody) = safeApiRequest {
        requestEmpty(apiService.changeTeacher(id, teacherBody))
    }

    override suspend fun registerTeacher(teacherBody: TeacherBody) = safeApiRequest {
        requestEmpty(apiService.registerTeacher(teacherBody))
    }
}