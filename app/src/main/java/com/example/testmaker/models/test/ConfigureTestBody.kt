package com.example.testmaker.models.test

import com.example.testmaker.models.student.Group

data class ConfigureTestBody(
    val availableAttempts: Int,
    val endTime: String,
    val groups: List<Group>,
    val randomAnswers: Boolean,
    val randomQuestions: Boolean,
    val startTime: String,
    val timeToSpend: String
)