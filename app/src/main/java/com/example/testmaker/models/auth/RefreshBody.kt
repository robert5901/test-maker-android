package com.example.testmaker.models.auth

import com.squareup.moshi.Json

data class RefreshBody(
    @Json(name = "refreshCode")
    val code: String
)