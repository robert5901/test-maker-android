package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.StudentAnswer
import com.example.testmaker.models.student.StudentResult
import com.example.testmaker.models.student.StudentTest
import com.example.testmaker.models.student.StudentTestQuestion
import com.example.testmaker.models.student.StudentTestStart
import com.example.testmaker.models.test.Answer
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.services.StudentService
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class StudentRepositoryImpl(private val apiService: StudentService): SuperRepository(), StudentRepository {
    override suspend fun getTests() = safeApiRequest {
        request(apiService.getTests())
    }

    override suspend fun startTest(testId: String) = safeApiRequest {
        request(apiService.startTest(testId))
    }

    override suspend fun getResults() = safeApiRequest {
        request(apiService.getResults())
    }

    override suspend fun finishTest(studentAnswers: List<StudentAnswer>) = safeApiRequest {
        request(apiService.finishTest(studentAnswers))
    }
}