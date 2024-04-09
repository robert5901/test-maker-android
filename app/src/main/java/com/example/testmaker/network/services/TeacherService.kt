package com.example.testmaker.network.services

import com.example.testmaker.models.teacher.StudentTestResult
import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.models.teacher.TeacherTestName
import com.example.testmaker.models.teacher.TeacherTestQuestionBody
import com.example.testmaker.models.test.ConfigureTestBody
import com.example.testmaker.models.test.Test
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.PartMap
import retrofit2.http.Path

interface TeacherService {
    @GET("/teacher/tests")
    suspend fun getAllTests(): Response<List<Test>>

    @GET("/teacher/tests/{id}")
    suspend fun getTest(@Path(value = "id", encoded = true) testId: String): Response<TeacherTest>

    @DELETE("/teacher/test/{id}")
    suspend fun deleteTest(@Path(value = "id", encoded = true) testId: String): Response<List<Test>>

    @GET("/teacher/test/new")
    suspend fun createTest(): Response<TeacherTest>

    @GET("/teacher/tests/{id}/results")
    suspend fun getResults(@Path(value = "id", encoded = true) testId: String): Response<List<StudentTestResult>>

    @PATCH("/teacher/test/{id}")
    suspend fun saveName(@Path(value = "id", encoded = true) testId: String,
                         @Body name: TeacherTestName): Response<TeacherTest>

    @DELETE("/teacher/test/{id}/{question_id}")
    suspend fun deleteQuestion(@Path(value = "id", encoded = true) testId: String,
                               @Path(value = "question_id", encoded = true) questionId: String): Response<TeacherTest>

    @Multipart
    @POST("/teacher/test/{id}/image/{question_id}")
    @JvmSuppressWildcards
    suspend fun addImage(@Path(value = "id", encoded = true) testId: String,
                         @Path(value = "question_id", encoded = true) questionId: String,
                         @PartMap params: Map<String, RequestBody>): Response<TeacherTest>

    @DELETE("/teacher/test/{id}/image/{question_id}")
    suspend fun deleteImage(@Path(value = "id", encoded = true) testId: String,
                            @Path(value = "question_id", encoded = true) questionId: String): Response<TeacherTest>

    @POST("/teacher/test/{id}/new_question")
    suspend fun createQuestion(@Path(value = "id", encoded = true) testId: String,
                               @Body question: TeacherTestQuestionBody): Response<TeacherTest>

    @PATCH("/teacher/test/{id}/{question_id}")
    suspend fun changeQuestion(@Path(value = "id", encoded = true) testId: String,
                               @Path(value = "question_id", encoded = true) questionId: String,
                               @Body question: TeacherTestQuestionBody): Response<TeacherTest>

    @PUT("/teacher/test/{id}")
    suspend fun saveConfig(@Path(value = "id", encoded = true) testId: String,
                           @Body configTestBody: ConfigureTestBody): Response<List<Test>>
}