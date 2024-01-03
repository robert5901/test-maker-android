package com.example.testmaker.models.teacher

data class TeacherTest(
    val id: String,
    val name: String?,
    val question: List<TeacherTestQuestion>?
)