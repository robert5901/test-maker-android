package com.example.testmaker.network.repositories

import android.net.Uri
import com.example.testmaker.TestMakerApplication
import com.example.testmaker.models.student.Group
import com.example.testmaker.models.teacher.StudentTestResult
import com.example.testmaker.models.teacher.TeacherTest
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
    override suspend fun getAllTests(): ApiResponse<List<Test>> {
        return ApiResponse.Success(TestListData.tests)
//        request(apiService.getAllTests())
    }

    override suspend fun getTest(testId: String): ApiResponse<TeacherTest> {
        return ApiResponse.Success(TestTeacherTest.teacherTest)
//        request(apiService.getTest(testId))
    }

    override suspend fun deleteTest(testId: String): ApiResponse<List<Test>> {
        return ApiResponse.Success(TestListData.tests)
//        request(apiService.deleteTest(testId))
    }

    override suspend fun createTest(): ApiResponse<TeacherTest> {
        return ApiResponse.Success(TestTeacherTest.teacherTest.copy(name = "", question = emptyList()))
//        request(apiService.createTest())
    }

    override suspend fun getResults(testId: String): ApiResponse<List<StudentTestResult>> {
        return ApiResponse.Success(listOf(
            StudentTestResult("50/50", "Жмышенко Валерий Альбертович", Group("1", "4480")),
            StudentTestResult("40/50", "Игонин Юрий Андреевич", Group("2", "4481")),
            StudentTestResult("30/50", "Игонян Фанзиль Фунялович", Group("3", "4482")),
            StudentTestResult("35/50", "Максимова Варвара Глебовна", Group("4", "4483")),
            StudentTestResult("21/50", "Алешин Кирилл Владимирович", Group("1", "4480")),
            StudentTestResult("46/50", "Юдин Илья Максимович", Group("2", "4481")),
            StudentTestResult("39/50", "Тимофеева Алиса Егоровна", Group("3", "4482"))
        ))
//        request(apiService.getResults(testId))
    }

    override suspend fun saveName(testId: String, name: String): ApiResponse<TeacherTest> {
        return ApiResponse.Success(TestTeacherTest.teacherTest.copy(name = name, question = emptyList()))
//        request(apiService.saveName(testId, name))
    }

    override suspend fun deleteQuestion(testId: String, questionId: String): ApiResponse<TeacherTest> {
        return ApiResponse.Success(TestTeacherTest.teacherTest.copy(question = listOf(
            TeacherTestQuestion(
                "1",
                "https://i.pinimg.com/originals/03/ab/0d/03ab0d21c9d9f2210e774a8b584ef962.png",
                true,
                listOf(
                    Answer("1", "ответ 1"),
                    Answer("2", "ответ 2"),
                    Answer("3", "ответ 3"),
                    Answer("4", "ответ 4")
                ),
                "Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса",
                listOf("1")
            )
        )))
//        request(apiService.deleteQuestion(testId, questionId))
    }

    override suspend fun addImage(fileUri: Uri): ApiResponse<TeacherTest> {
        return ApiResponse.Success(TestTeacherTest.teacherTest.copy(question = listOf(
            TeacherTestQuestion(
                "1",
                "https://i.pinimg.com/originals/03/ab/0d/03ab0d21c9d9f2210e774a8b584ef962.png",
                true,
                listOf(
                    Answer("1", "ответ 1"),
                    Answer("2", "ответ 2"),
                    Answer("3", "ответ 3"),
                    Answer("4", "ответ 4")
                ),
                "Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса",
                listOf("1")
            )
        )))
//        request(apiService.addImage(getFileMap(fileUri))
    }

    override suspend fun deleteImage(testId: String, questionId: String): ApiResponse<TeacherTest> {
        return ApiResponse.Success(TestTeacherTest.teacherTest)
//        request(apiService.deleteImage(testId, questionId)
    }

    override suspend fun createQuestion(testId: String, question: TeacherTestQuestionBody): ApiResponse<TeacherTest> {
        return ApiResponse.Success(TestTeacherTest.teacherTest)
//        request(apiService.createQuestion(testId, question)
    }

    override suspend fun saveConfig(testId: String, configTestBody: ConfigureTestBody): ApiResponse<Test> {
        return ApiResponse.Success(TestListData.tests[0])
//        request(apiService.saveConfig(testId, configTestBody)
    }

    private fun getFileMap(fileUri: Uri): Map<String, RequestBody> {
        val contentResolver = TestMakerApplication.applicationContext().contentResolver
        val contentPart = InputStreamRequestBody("image/*".toMediaTypeOrNull(), contentResolver, fileUri)

        return mapOf(Pair<String, RequestBody>("file\"; filename=\"filename.jpg\"", contentPart))
    }
}