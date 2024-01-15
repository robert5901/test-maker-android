package com.example.testmaker.models.student

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentTest(
    val id: String,
    val availableAttempts: Int,
    val name: String,
    val questionsNumber: Int,
    val teacherName: String,
    val timeToSpend: String
): Parcelable