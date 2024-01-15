package com.example.testmaker.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerErrorResponse (
    val code: Int,
    val message: String?
)