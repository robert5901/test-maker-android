package com.example.testmaker.models.teacher

import com.example.testmaker.models.test.Answer

data class TeacherTestQuestion(
    val id: String,
    val imageUrl: String?,
    val isSingleQuestion: Boolean,
    val possibleAnswers: List<Answer>,
    val question: String,
    val rightAnswers: List<String>
)