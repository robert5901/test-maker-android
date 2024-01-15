package com.example.testmaker.models.student

import android.os.Parcelable
import com.example.testmaker.models.test.Answer
import kotlinx.parcelize.Parcelize

@Parcelize
class StudentTestQuestion(
    val id: String,
    val imageUrl: String?,
    val isSingleAnswer: Boolean,
    val possibleAnswers: List<Answer>,
    val question: String,
): Parcelable