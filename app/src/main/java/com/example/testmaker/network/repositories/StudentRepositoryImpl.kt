package com.example.testmaker.network.repositories

import com.example.testmaker.models.student.StudentAnswer
import com.example.testmaker.models.student.StudentResult
import com.example.testmaker.models.student.StudentTest
import com.example.testmaker.models.student.StudentTestQuestion
import com.example.testmaker.models.student.StudentTestStart
import com.example.testmaker.models.test.Answer
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.services.StudentService

class StudentRepositoryImpl(private val apiService: StudentService): SuperRepository(), StudentRepository {
    override suspend fun getTests(): ApiResponse<List<StudentTest>> {
        return ApiResponse.Success( listOf(
            StudentTest("1", 2, "Методы оптимизации", 2,
                "Хазипова Альсина Айдаровна", "20"),
            StudentTest("2", 3, "Методы оптимизации 1", 2,
                "Быкова Вероника Саввична", "25"),
            StudentTest("3", 1, "Методы оптимизации 2", 2,
                "Гришин Максим Владимирович", "30"),
            StudentTest("4", 1, "Методы оптимизации 3", 2,
                "Орлов Адам Михайлович", "15")
        ))
//        request(apiService.getTests())
    }

    override suspend fun startTest(testId: String): ApiResponse<StudentTestStart> {
        return ApiResponse.Success(StudentTestStart(
            "1", "2024-01-05T13:11:05.912Z",
            listOf(
                StudentTestQuestion(
                    "1",
                    "https://i.pinimg.com/originals/03/ab/0d/03ab0d21c9d9f2210e774a8b584ef962.png",
                    true,
                    listOf(
                        Answer("1", "1ответ 1"),
                        Answer("2", "1ответ 2"),
                        Answer("3", "1ответ 3"),
                        Answer("4", "1ответ 4")
                    ),
                    "Текст вопроса1 Текст вопроса1 Текст вопроса1 Текст вопроса1 Текст вопроса1 Текст вопроса1 Текст вопроса1 Текст вопроса1"
                ),
                StudentTestQuestion(
                    "2",
                    "",
                    false,
                    listOf(
                        Answer("1", "2ответ 1"),
                        Answer("2", "2ответ 2"),
                        Answer("3", "2ответ 3"),
                        Answer("4", "2ответ 4")
                    ),
                    "Текст вопроса2 Текст вопроса2 Текст вопроса2 Текст вопроса2 Текст вопроса2 Текст вопроса2 Текст вопроса2"
                ),
                StudentTestQuestion(
                    "3",
                    "",
                    true,
                    listOf(
                        Answer("1", "3ответ 1"),
                        Answer("2", "3ответ 2"),
                        Answer("3", "3ответ 3"),
                        Answer("4", "3ответ 4")
                    ),
                    "Текст вопроса3 Текст вопроса3 Текст вопроса3 Текст вопроса3 Текст вопроса3 Текст вопрос3 Текст вопроса3"
                )
            )
        ))
//        request(apiService.startTest(testId))
    }

    override suspend fun getResults(): ApiResponse<List<StudentResult>> {
        return ApiResponse.Success(listOf(
            StudentResult("1", "23.01.2024 13:53", "Методы оптимизации",
                "50/50", "Хазипова Альсина Айдаровна"),
            StudentResult("2", "23.01.2024 13:53", "Методы оптимизации 1",
                "20/50", "Гришин Максим Владимирович"),
            StudentResult("3", "23.01.2024 13:53", "Методы оптимизации 2",
                "30/50", "Быкова Вероника Саввична"),
            StudentResult("4", "23.01.2024 13:53", "Методы оптимизации 3",
                "32/50", "Орлов Адам Михайлович")
        ))
//        request(apiService.getResults())
    }

    override suspend fun finishTest(studentAnswers: List<StudentAnswer>): ApiResponse<StudentResult> {
        return ApiResponse.Success(StudentResult("1", "23.01.2024 13:53", "Методы оптимизации",
            "50/50", "Хазипова Альсина Айдаровна"))
//        request(apiService.finishTest(studentAnswers))
    }
}