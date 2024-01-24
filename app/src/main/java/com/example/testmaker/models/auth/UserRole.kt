package com.example.testmaker.models.auth

enum class UserRole {
    ADMIN,
    STUDENT,
    TEACHER;

    companion object {
        fun fromString(role: String): UserRole = when (role) {
            "admin" -> ADMIN
            "student" -> STUDENT
            "teacher" -> TEACHER
            else -> STUDENT
        }
    }
}