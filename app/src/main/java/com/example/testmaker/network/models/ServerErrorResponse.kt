package com.example.testmaker.network.models

import com.squareup.moshi.JsonClass

// TODO что приходит при ошибке. посмотреть в swagger
@JsonClass(generateAdapter = true)
data class ServerErrorResponse (
    val code: Int,
    val message: String?
)