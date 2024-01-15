package com.example.testmaker.ui

import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.models.teacher.TeacherTestQuestion
import com.example.testmaker.models.test.Answer

object TestTeacherTest {
    val teacherTest =
        TeacherTest(
            "1",
            "Методы оптимизации",
            listOf(
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
                ),
                TeacherTestQuestion(
                    "2",
                    "",
                    false,
                    listOf(
                        Answer("1", "ответ 1"),
                        Answer("2", "ответ 2"),
                        Answer("3", "ответ 3"),
                        Answer("4", "ответ 4")
                    ),
                    "Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса",
                    listOf("2")
                )
            )
        )
}