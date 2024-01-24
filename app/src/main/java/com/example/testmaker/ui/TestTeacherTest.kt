package com.example.testmaker.ui

import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.models.teacher.TeacherTestQuestion
import com.example.testmaker.models.test.Answer

object TestTeacherTest {
    val teacherTest =
        TeacherTest(
            "1",
            "Основы Java",
            listOf(
                TeacherTestQuestion(
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
                    listOf("1")
                ),
                TeacherTestQuestion(
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
                    listOf("2")
                ),
                TeacherTestQuestion(
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
                    listOf("2")
                )
            )
        )
}