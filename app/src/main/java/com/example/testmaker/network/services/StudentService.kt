package com.example.testmaker.network.services

import com.example.testmaker.models.student.StudentAnswer
import com.example.testmaker.models.student.StudentResult
import com.example.testmaker.models.student.StudentTest
import com.example.testmaker.models.student.StudentTestStart
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface StudentService {
    @GET("/student/tests")
    suspend fun getTests(): Response<List<StudentTest>>

    @GET("/student/start/{id}")
    suspend fun startTest(testId: String): Response<StudentTestStart>

    @GET("/student/results")
    suspend fun getResults(): Response<List<StudentResult>>

    @POST("/student/end/{id}")
    suspend fun finishTest(studentAnswers: List<StudentAnswer>): Response<StudentResult>
}