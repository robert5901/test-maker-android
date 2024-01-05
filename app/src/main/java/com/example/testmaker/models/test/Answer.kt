package com.example.testmaker.models.test

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(
    val id : String,
    val answer: String
): Parcelable