package com.example.testmaker.models.auth

data class AuthInfo (
    val accessCode: String,
    val refreshCode: String,
    val userRole: UserRole
)