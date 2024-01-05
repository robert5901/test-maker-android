package com.example.testmaker.models.student

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentTestStart(
    val id: String,
    val doneTime: String,
    val questions: List<StudentTestQuestion>
): Parcelable