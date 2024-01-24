package com.example.testmaker.ui

import com.example.testmaker.models.student.Group
import com.example.testmaker.models.test.Test

object TestListData {
    val tests = listOf(
        Test(
            "1", 2, "2023-12-25T17:10:04.865Z",
            listOf(
                Group("1", "4480"),
                Group("2", "4481"),
                Group("3", "4482"),
                Group("4", "4483")
            ),
            "Методы оптимизации", true, false,
            "2023-12-25T17:10:04.865Z", "PT20M"
        ),
        Test(
            "2", 2, "2023-12-25T17:10:04.865Z",
            listOf(),
            "Информатика", true, true,
            "2023-12-25T17:10:04.865Z", "PT20M"
        ),
        Test(
            "3", 2, "2023-12-25T17:10:04.865Z",
            listOf(
                Group("1", "4480"),
                Group("2", "4481"),
                Group("3", "4482"),
                Group("4", "4483")
            ),
            "Математика", true, true,
            "2023-12-25T17:10:04.865Z", "PT20M"
        ),
        Test(
            "4", 2, "2023-12-25T17:10:04.865Z",
            listOf(
                Group("1", "4480"),
                Group("2", "4481"),
                Group("3", "4482"),
                Group("4", "4483")
            ),
            "Основы программирования", true, true,
            "2023-12-25T17:10:04.865Z", "PT20M"
        ),
        Test(
            "5", 2, "2023-12-25T17:10:04.865Z",
            listOf(
                Group("1", "4480"),
                Group("2", "4481"),
                Group("3", "4482"),
                Group("4", "4483")
            ),
            "Базы данных", true, true,
            "2023-12-25T17:10:04.865Z", "PT20M"
        ),
        Test(
            "6", 2, "2023-12-25T17:10:04.865Z",
            listOf(
                Group("1", "4480"),
                Group("2", "4481"),
                Group("3", "4482"),
                Group("4", "4483")
            ),
            "Программирование на Java", true, true,
            "2023-12-25T17:10:04.865Z", "PT20M"
        ),Test(
            "6", 2, "2023-12-25T17:10:04.865Z",
            listOf(
                Group("1", "4480"),
                Group("2", "4481"),
                Group("3", "4482"),
                Group("4", "4483")
            ),
            "Основы Java", true, true,
            "2023-12-25T17:10:04.865Z", "PT20M"
        ),
    )
}