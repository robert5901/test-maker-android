package com.example.testmaker.models.student

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val id: String,
    val title: String
): Parcelable