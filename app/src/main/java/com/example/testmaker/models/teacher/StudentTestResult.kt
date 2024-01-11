package com.example.testmaker.models.teacher

import com.example.testmaker.models.student.Group
import com.squareup.moshi.Json

data class StudentTestResult(
    @Json(name = "resul")
    val result: String,
    val studentName: String,
    val studentGroup: Group
)