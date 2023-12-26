package com.example.testmaker.models.test

import android.os.Parcelable
import com.example.testmaker.models.student.Group
import kotlinx.parcelize.Parcelize

@Parcelize
data class Test(
    val id: String,
    val availableAttempts: Int,
    val endTime: String,
    val groups: List<Group>,
    val name: String,
    val randomAnswers: Boolean,
    val randomQuestions: Boolean,
    val startTime: String,
    val timeToSpend: String,
): Parcelable