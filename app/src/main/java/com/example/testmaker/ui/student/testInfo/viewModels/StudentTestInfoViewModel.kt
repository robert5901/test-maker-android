package com.example.testmaker.ui.student.testInfo.viewModels

import androidx.lifecycle.ViewModel
import com.example.testmaker.models.student.StudentTestQuestion
import com.example.testmaker.models.student.StudentTestStart
import com.example.testmaker.models.test.Answer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudentTestInfoViewModel: ViewModel(

) {
    private val _test = MutableStateFlow<StudentTestStart?>(null)
    val test = _test.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun startTest() {
        // TODO test data
        _test.tryEmit(
            StudentTestStart(
                "1", "2024-01-05T13:11:05.912Z",
                listOf(
                    StudentTestQuestion(
                        "1",
                        "https://i.pinimg.com/originals/03/ab/0d/03ab0d21c9d9f2210e774a8b584ef962.png",
                        true,
                        listOf(
                            Answer("1", "ответ 1"),
                            Answer("2", "ответ 2"),
                            Answer("3", "ответ 3"),
                            Answer("4", "ответ 4")
                        ),
                        "Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса"
                    ),
                    StudentTestQuestion(
                        "2",
                        "",
                        false,
                        listOf(
                            Answer("1", "ответ 1"),
                            Answer("2", "ответ 2"),
                            Answer("3", "ответ 3"),
                            Answer("4", "ответ 4")
                        ),
                        "Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса"
                    )
                )
            )
        )
    }

    fun finishTest() {

    }
}