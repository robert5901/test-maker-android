package com.example.testmaker.models.teacher

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeacherTest(
    val id: String,
    val name: String?,
    val question: List<TeacherTestQuestion>?
): Parcelable