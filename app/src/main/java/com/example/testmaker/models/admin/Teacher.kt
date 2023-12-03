package com.example.testmaker.models.admin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Teacher(
    val id: String,
    val login: String,
    val name: String
): Parcelable