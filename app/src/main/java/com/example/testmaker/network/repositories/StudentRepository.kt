package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.StudentAnswer
import com.example.testmaker.models.student.StudentResult
import com.example.testmaker.models.student.StudentTest
import com.example.testmaker.models.student.StudentTestStart
import com.example.testmaker.network.models.ApiResponse

interface StudentRepository {
    suspend fun getTests(): ApiResponse<List<StudentTest>>

    suspend fun startTest(testId: String): ApiResponse<StudentTestStart>

    suspend fun getResults(): ApiResponse<List<StudentResult>>

    suspend fun finishTest(studentAnswers: List<StudentAnswer>): ApiResponse<StudentResult>
}