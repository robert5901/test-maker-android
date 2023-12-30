package com.example.testmaker.models.teacher

import com.example.testmaker.models.student.Group

data class StudentTestResult(
    val result: String,
    val studentName: String,
    val studentGroup: List<Group>
)