package com.example.testmaker.network.repositories

import android.net.Uri
import com.example.testmaker.TestMakerApplication
import com.example.testmaker.models.student.Group
import com.example.testmaker.models.teacher.StudentTestResult
import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.models.teacher.TeacherTestName
import com.example.testmaker.models.teacher.TeacherTestQuestion
import com.example.testmaker.models.teacher.TeacherTestQuestionBody
import com.example.testmaker.models.test.Answer
import com.example.testmaker.models.test.ConfigureTestBody
import com.example.testmaker.models.test.Test
import com.example.testmaker.network.InputStreamRequestBody
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.services.TeacherService
import com.example.testmaker.ui.TestListData
import com.example.testmaker.ui.TestTeacherTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class TeacherRepositoryImpl(private val apiService: TeacherService): SuperRepository(), TeacherRepository {
    override suspend fun getAllTests() = safeApiRequest {
        request(apiService.getAllTests())
    }

    override suspend fun getTest(testId: String) = safeApiRequest {
        request(apiService.getTest(testId))
    }

    override suspend fun deleteTest(testId: String) = safeApiRequest {
        request(apiService.deleteTest(testId))
    }

    override suspend fun createTest() = safeApiRequest {
        request(apiService.createTest())
    }

    override suspend fun getResults(testId: String) = safeApiRequest {
        request(apiService.getResults(testId))
    }

    override suspend fun saveName(testId: String, name: TeacherTestName) = safeApiRequest {
        request(apiService.saveName(testId, name))
    }

    override suspend fun deleteQuestion(testId: String, questionId: String) = safeApiRequest {
        request(apiService.deleteQuestion(testId, questionId))
    }

    override suspend fun addImage(testId: String, questionId: String, fileUri: Uri) = safeApiRequest{
        request(apiService.addImage(testId, questionId, getFileMap(fileUri)))
    }

    override suspend fun deleteImage(testId: String, questionId: String) = safeApiRequest {
        request(apiService.deleteImage(testId, questionId))
    }

    override suspend fun createQuestion(testId: String, question: TeacherTestQuestionBody) = safeApiRequest {
        request(apiService.createQuestion(testId, question))
    }

    override suspend fun changeQuestion(testId: String, questionId: String, question: TeacherTestQuestionBody) = safeApiRequest {
        request(apiService.changeQuestion(testId, questionId, question))
    }

    override suspend fun saveConfig(testId: String, configTestBody: ConfigureTestBody) = safeApiRequest {
        request(apiService.saveConfig(testId, configTestBody))
    }

    private fun getFileMap(fileUri: Uri): Map<String, RequestBody> {
        val contentResolver = TestMakerApplication.applicationContext().contentResolver
        val contentPart = InputStreamRequestBody("image/*".toMediaTypeOrNull(), contentResolver, fileUri)

        return mapOf(Pair<String, RequestBody>("file\"; filename=\"filename.jpg\"", contentPart))
    }
}