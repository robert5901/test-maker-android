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
            StudentTest("1", 2, "Методы оптимизации", 3,
                "Хазипова Альсина Айдаровна", "PT20M"),
            StudentTest("2", 3, "Информатика", 25,
                "Быкова Вероника Саввична", "PT25M"),
            StudentTest("3", 1, "Математика", 15,
                "Гришин Максим Владимирович", "PT30M"),
            StudentTest("4", 1, "Основы Java", 3,
                "Орлов Адам Михайлович", "PT15M"),
        ))
//        request(apiService.getTests())
    }

    override suspend fun startTest(testId: String): ApiResponse<StudentTestStart> {
        return ApiResponse.Success(StudentTestStart(
            "1", "2024-01-05T13:11:05.912Z",
            listOf(
                StudentTestQuestion(
                    "1",
                    "",
                    true,
                    listOf(
                        Answer("1", "Не более 3"),
                        Answer("2", "Не более 10"),
                        Answer("3", "Не более 5"),
                        Answer("4", "Неограниченное количество")
                    ),
                    "Сколько параметров может принимать функция?",
                    ),
                StudentTestQuestion(
                    "2",
                    "https://media.tproger.ru/quiz/1691149986767Frame%204.png",
                    false,
                    listOf(
                        Answer("1", "В консоль будет выведено Hello!"),
                        Answer("2", "NullPointerException"),
                        Answer("3", "Код выполнится без ошибок, в консоль ничего не выведется"),
                        Answer("4", "Ошибка компиляции")
                    ),
                    "Что будет, если скомпилировать и выполнить код:",
                    ),
                StudentTestQuestion(
                    "3",
                    "",
                    false,
                    listOf(
                        Answer("1", "Названия"),
                        Answer("2", "Тип данных"),
                        Answer("3", "Размер"),
                        Answer("4", "Адрес в памяти")
                    ),
                    "Что общего у всех элементов массива?",
                    )
            )
        ))
//        request(apiService.startTest(testId))
    }

    override suspend fun getResults(): ApiResponse<List<StudentResult>> {
        return ApiResponse.Success(listOf(
            StudentResult("1", "2024-01-25T11:10:16.912Z", "Методы оптимизации",
                "4/4", "Хазипова Альсина Айдаровна"),
            StudentResult("2", "2024-01-25T11:47:16.912Z", "Информатика",
                "19/25", "Гришин Максим Владимирович"),
            StudentResult("3", "2024-01-25T12:23:16.912Z", "Математика",
                "5/15", "Быкова Вероника Саввична"),
            StudentResult("4", "2024-01-25T12:46:16.912Z", "Основы Java",
                "3/4", "Орлов Адам Михайлович")
        ))
//        request(apiService.getResults())
    }

    override suspend fun finishTest(studentAnswers: List<StudentAnswer>): ApiResponse<StudentResult> {
        return ApiResponse.Success(StudentResult("1", "23.01.2024 13:53", "Методы оптимизации",
            "3/4", "Хазипова Альсина Айдаровна"))
//        request(apiService.finishTest(studentAnswers))
    }
}