package com.example.testmaker.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// TODO что приходит при ошибке. посмотреть в swagger
@JsonClass(generateAdapter = true)
data class ServerErrorResponse (
    @Json(name="message")
    val message: String?,
    @Json(name="system_message")
    val systemMessage: String?
)