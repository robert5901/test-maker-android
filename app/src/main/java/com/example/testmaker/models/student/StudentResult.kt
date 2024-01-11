package com.example.testmaker.models.student

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentResult(
    val id: String,
    val doneTime: String,
    val name: String,
    val result: String,
    val teacherName: String
): Parcelable