package com.example.testmaker.network.repositories

import android.net.Uri
import com.example.testmaker.models.teacher.StudentTestResult
import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.models.teacher.TeacherTestQuestionBody
import com.example.testmaker.models.test.ConfigureTestBody
import com.example.testmaker.models.test.Test
import com.example.testmaker.network.models.ApiResponse

interface TeacherRepository {
    suspend fun getAllTests(): ApiResponse<List<Test>>

    suspend fun getTest(testId: String): ApiResponse<TeacherTest>

    suspend fun deleteTest(testId: String): ApiResponse<List<Test>>

    suspend fun createTest(): ApiResponse<TeacherTest>

    suspend fun getResults(testId: String): ApiResponse<List<StudentTestResult>>

    suspend fun saveName(testId: String, name: String): ApiResponse<TeacherTest>

    suspend fun deleteQuestion(testId: String, questionId: String): ApiResponse<TeacherTest>

    suspend fun addImage(fileUri: Uri): ApiResponse<TeacherTest>

    suspend fun deleteImage(testId: String, questionId: String): ApiResponse<TeacherTest>

    suspend fun createQuestion(testId: String, question: TeacherTestQuestionBody): ApiResponse<TeacherTest>

    suspend fun saveConfig(testId: String, configTestBody: ConfigureTestBody): ApiResponse<Test>
}