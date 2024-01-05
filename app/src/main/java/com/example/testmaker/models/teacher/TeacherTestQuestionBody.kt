package com.example.testmaker.models.teacher

data class TeacherTestQuestionBody(
    val answers: List<String>,
    val isSingleQuestion: Boolean,
    val question: String,
    val rightAnswers: List<Int>
)